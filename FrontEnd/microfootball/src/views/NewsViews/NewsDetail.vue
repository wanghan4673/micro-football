<template>
    <div class="grid-newsdetail-container">
        <div>
            <BackButton />
        </div>
        <div class="news-detail-container">
            <div class="news-title-box">
                {{ newsDetail.title }}
            </div>
            <div class="news-tags-box">
                <div>
                    <a-tag v-for="(tag, index) in newsDetail.tags" :key="index" :color="getColor(index)" :bordered="false"
                        style="font-size: 2vh;">{{ tag }}</a-tag>
                </div>
            </div>
            <div class="news-content-box">
                <div v-html="newsDetail.content" class="content-text-box">
                </div>
                <div class="content-pic-box">
                    <div v-for="(pic, index) in newsDetail.picUrls" :key="index" style="margin-bottom: 4vh;">
                        <el-image :src="pic" :preview-src-list="newsDetail.picUrls" :initial-index="index" />
                    </div>
                </div>
            </div>
        </div>
        <div class="cat-rope-container">
            <div class="back-to-top" :class="{ 'animating': isScrollingToTop }" @click="scrollToTop"></div>
            <el-button class="report-button" @click="judgeReportNews()">举报</el-button>
        </div>
        <el-dialog title="填写举报理由" v-model="reportVisible" class="report-dialog-box">
            <el-input style="margin-bottom: 2vh;" v-model="reportContent" placeholder="请输入举报理由" />
            <div slot="footer" class="dialog-footer">
                <el-button @click="reportVisible = false">取消</el-button>
                <el-button type="primary" @click="reportNews()">提交</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import BackButton from '../../components/BackButton.vue'
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import axios from 'axios';

const newsDetail = ref({
    id: '',
    title: '',
    content: '',
    createTime: '',
    tags: [],
    picUrls: [],
})
const isScrollingToTop = ref(false)
const isLoggedIn = ref(false)
const hasReported = ref(false)
const userName = ref('')
const reportVisible = ref(false)
const reportContent = ref('')

onMounted(() => {
    const route = useRoute()
    const id = route.query.id
    if (id && typeof id === 'string') {
        newsDetail.value.id = id
    }
    getNewsDetail(id)
    getReportStatus(id)
})

const getNewsDetail = async (id: any) => {
    try {
        const response = await axios.get(`/api/news/detail/${id}`)
        const data = response.data.data
        if (response.data.code === 1) {
            newsDetail.value = {
                id,
                title: data.title,
                content: data.content.replace(/\\n/g, '<br>'),
                createTime: data.createTime,
                tags: data.tags ? data.tags.split(',') : [],
                picUrls: data.picUrl ? data.picUrl.split(',') : [],
            }
        } else {
            showError('获取新闻详情失败!')
        }
    } catch (error) {
        showError('获取新闻详情发送失败')
    }
}

const getReportStatus = async (id: any) => {
    const token = localStorage.getItem('token')
    try {
        const response = await axios.get(`/api/token-news/detail/${id}/report-status`,{
            headers: {
                'token': token,
            }
        })
        if (response.data.code === 1) {
            const msg = response.data.data
            if (msg === 'noLogin') {
                isLoggedIn.value = false
            } else if (msg === 'isReported') {
                isLoggedIn.value = true
                hasReported.value = true
                getName()
            } else {
                isLoggedIn.value = true
                hasReported.value = false
                getName()
            }
        } else {
            showError('获取举报状态失败!')
        }
    } catch (error) {
        showError('获取举报状态请求发送失败')
    }
}

const reportNews = async () => {
    try {
        const token = localStorage.getItem('token')
        let formData = new FormData()
        formData.append("name", userName.value)
        formData.append("newsId", newsDetail.value.id)
        formData.append("content", reportContent.value)
        const response = await axios.post('/api/token-news/report', formData,{
            headers: {
                'token': token,
            }
        })
        if (response.data.code === 1) {
            ElMessage({
                message: '举报成功,感谢您的反馈!',
                type: 'success'
            })
        } else {
            showError('举报失败!')
        }
    } catch (error) {
        showError('举报请求发送失败')
    }
}

const getName = async () => {
    const token = localStorage.getItem('token');
    try {
        const response = await axios.get('/api/users/login-status', {
            headers: {
                'token': token
            }
        })
        if (response.data.code == 1 && response.data.data != null) {
            userName.value = response.data.data.name;
        }
    } catch (err) {
        showError('获取用户名请求发送失败')
    }
}

const showError = (message: string) => {
    ElMessage({
        message,
        type: 'error',
    })
}

const judgeReportNews = () => {
    if (!isLoggedIn.value) {
        showError('您未登录,无法举报!')
        return
    } else if (hasReported.value) {
        showError('您已举报过该新闻,管理员正在审核中!')
        return
    } else {
        reportVisible.value = true
    }
}


const getColor = (index: number) => {
    // 获取标签颜色
    const colors = ['pink', 'orange', 'cyan', 'blue'];
    return colors[index % colors.length];
}

const scrollToTop = () => {
    // 获取news-content-box元素
    const newsContentBox = document.querySelector('.news-content-box');
    if (newsContentBox) {
        isScrollingToTop.value = true;
        newsContentBox.scrollTo({
            top: 0,
            behavior: 'smooth',
        });
        newsContentBox.addEventListener('scroll', () => {
            if (newsContentBox.scrollTop === 0) {
                isScrollingToTop.value = false;
            }
        });
    }
}
</script>

<style lang="scss">
@import '../../assets/css/frame.scss';

.grid-newsdetail-container {
    @extend .grid-three-container;
    grid-template-columns: 1fr 5fr 1fr;
}

.news-detail-container {
    height: 90vh;
    background-color: #f7f7f8;
    display: flex;
    flex-direction: column;

    .news-title-box {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 15vh;
        font-size: 5vh;
    }

    .news-tags-box {
        display: flex;
        justify-content: end;
        height: 5vh;
    }

    .news-content-box {
        height: 75vh;
        width: 85%;
        align-self: center;
        font-size: 2.6vh;
        overflow: auto;
        overflow-y: auto;

        .content-text-box {
            margin-bottom: 4vh;
        }
    }

    .news-content-box::-webkit-scrollbar {
        width: 0;
    }
}

.cat-rope-container {
    display: flex;
    flex-direction: column;

    .back-to-top {
        cursor: pointer;
        top: 0;
        width: 70px;
        height: 590px; // 图片自身是px
        margin-left: 35%;
        background: url(../../assets/img/scroll_cat.png);
        transition: transform 0.5s ease-in-out, opacity 0.5s ease-in-out;
        opacity: 1;
    }

    .back-to-top.animating {
        transform: translateY(-200px);
        opacity: 1;
    }

    .report-button {
        align-self: center;
        width: 40%;
        margin-top: 2vh;
        cursor: pointer;
        transition: transform 0.3s ease;

        &:hover {
            transform: scale(1.1);
        }
    }
}

.report-dialog-box {
    position: absolute;
    display: flex;
    flex-direction: column;
    top: 40%;
    left: 50%;
    transform: translate(-50%, -75%);
    width: 35vw;
}
</style>