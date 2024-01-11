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
                <div class="content-text-box">
                    {{ newsDetail.content }}
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
        </div>
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

onMounted(() => {
    const route = useRoute()
    const id = route.query.id
    if (id && typeof id === 'string') {
        newsDetail.value.id = id
    }
    console.log("id is " + id)
    getNewsDetail(id)
})

const getNewsDetail = async (id: any) => {
    try {
        const response = await axios.get(`/api/news/detail/${id}`)
        const data = response.data.data
        if (response.data.code === 1) {
            newsDetail.value = {
                id,
                title: data.title,
                content: data.content,
                createTime: data.createTime,
                tags: data.tags ? data.tags.split(',') : [],
                picUrls: data.picUrl ? data.picUrl.split(',') : [],
            }
            console.log(newsDetail.value)
        } else {
            showError('获取新闻详情失败!')
        }
    } catch (error) {
        showError('获取新闻详情发送失败')
    }
}

const showError = (message: string) => {
    ElMessage({
        message,
        type: 'error',
    })
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
        height: 10vh;
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

.back-to-top {
    cursor: pointer;
    top: 0;
    width: 70px;
    height: 590px;
    margin-left: 35%;
    background: url(../../assets/img/scroll_cat.png);
    transition: transform 0.5s ease-in-out, opacity 0.5s ease-in-out;
    opacity: 1;
}

.back-to-top.animating {
    transform: translateY(-200px);
    opacity: 1;
}
</style>