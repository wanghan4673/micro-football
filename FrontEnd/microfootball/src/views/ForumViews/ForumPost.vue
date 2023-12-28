<template>
    <div style="display: flex; margin-left: 20px;">
        <el-icon @click="back" style="margin-top: 20px; margin-left: 10px;">
            <Back />
        </el-icon>
        <div class="QC-title" v-if="post.title">
            <p style="margin-top: 16px; margin-left: 20px;">{{ post.title }}</p>
        </div>
    </div>

    <div id="QC-bg">
        <div id="Box" v-if="post.content">
            <div style="width: 100%;word-wrap: break-word; margin-top: 5px;margin-bottom:5px ; ">
                <img :src="poster.avatar" alt="">
                <span> {{ poster.name }}</span>
            </div>
            <div style="width: 100%;word-wrap: break-word">
                <p v-html="post.content"></p>
            </div>
        </div>
        <!-- v-if="props.Question.img && props.Question.img.length > 0"  -->
        <div id="Box" v-if="post.img">
            <div v-for="img of post.img" :key="img">
                <img :src="img" alt="图片加载失败" style="max-width: 60%;" />
            </div>
        </div>
        <div style="display: flex; gap:20px;margin:20px;">
            <el-icon :size="20" :color="isliked ? '#d57eb7' : ''" @click="likeclick">
                <CircleCheck />
            </el-icon>
            <span style="font-size: 20px; margin-top: -4px;">{{ post.likes }}</span>
            <el-icon :size="20" :color="iscollected ? '#d57eb7' : ''" @click="collectclick">
                <Star />
            </el-icon>
            <span style="font-size: 20px;margin-top: -4px;">{{ post.collect }}</span>

            <el-popover :visible="visible" placement="bottom" :width="360">
                <p style="font-size: 16px;padding-left: 5px; margin-bottom: 15px;">举报理由：</p>
                <el-input v-model="reportreason" placeholder="举报理由" :rows="3" type="textarea" clearable />
                <div style="text-align: right; margin: 10px">
                    <el-button type="primary" @click="handleSubmitClick">提交举报</el-button>
                </div>
                <template #reference>
                    <div @click="visible = true"
                        style="display: flex; align-items: center; cursor: pointer;margin-top: -4px;">
                        <el-icon :size="20" :color="iscollected ? '#d57eb7' : ''">
                            <WarningFilled />
                        </el-icon>
                        <span style="font-size: 14px; margin-left: 5px;">举报</span>
                    </div>
                </template>
            </el-popover>

        </div>
    </div>
    <p style="font-size: large; font-weight: 400; margin: 20px;margin-left: 50px;">评论 {{ comments ? comments.length : 0 }}
    </p>
    <div id="QC-bg" v-if="comments != []">
        <div id="Box" style="height: auto;padding-top: 20px;" v-for="(comment, index) of comments" :key="index">
            <div style="width: 100%; display:flex; justify-content:space-between; border-bottom: 1px solid #44484a;">
                <div style="max-width:70% ; word-wrap: break-word; overflow: auto;">{{ comment.comment }}</div>
                <div style="max-width: 25%; display: flex;">
                    <img :src="comment.avatar" alt="">
                    <p>{{ comment.name }}</p>
                </div>
            </div>
        </div>
    </div>
    <div id="newcomment">
        <el-input v-model="newcomment" placeholder="发布评论" style="height: 100%;width: 60%;"></el-input>
        <el-button color="#5a9feacc" @click="commentclick">发布评论</el-button>
    </div>
</template>

<script setup>
import { defineProps, onMounted, ref } from "vue"
import { useRouter, useRoute } from "vue-router"
import axios from "axios"
const router = useRouter()
const route = useRoute()
import { useForumStore } from '../../stores/forum.ts'
import { useGeneralStore } from '../../stores/general.ts'
import { ElMessage } from 'element-plus'
const forumstore = useForumStore()
const back = () => {
    router.back();
}
let store = useGeneralStore()
let postid = route.query.id;
let post = ref({ title: '', content: '', img: null })
let comments = ref([])
let poster = ref(null)
let isliked = ref(false)
let iscollected = ref(false)
let newcomment = ref("")
let reportreason = ref('')
onMounted(() => {
    loadPost(postid)
    getUserProfile()
})

const loadPost = async (postid) => {
    let response
    let token = localStorage.getItem('token')
    try {
        response = await axios.get("/api/forum/post/" + postid, {
            headers: {
                'token': `${token}`
            }
        });
        if (response.status == 200) {
            console.log(response.data)
            post.value = response.data.data.post
            comments.value = response.data.data.commentInfos
            poster.value = response.data.data.user
            isliked.value = response.data.data.isliked
            iscollected.value = response.data.data.iscollected
        }
    } catch (error) {
        console.log(error)
        ElMessage({
            message: "获取失败，请先登录",
            type: "error"
        })
    }
}

const getUserProfile = async () => {
    const token = localStorage.getItem('token')
    try {
        const response = await axios.get('/api/user/score', {
            headers: {
                'token': token,
            }
        })
        if (response.data.code == 1) {
            store.user.username.value = response.data.data.name
        }
    } catch (error) {
    }
}
const handleSubmitClick = async () => {
    let response
    let token = localStorage.getItem('token')
    try {
        response = await axios.post("/api/forum/report?reporterName=" + store.username + "&reason=" + reportreason.value + "&postId=" + postid, {
        }, {
            headers: {
                'token': `${token}`,
            }
        });
        if (response.status == 200) {
            ElMessage({
                message: "举报成功",
                type: "success"
            })
            loadPost(postid)
        }
    } catch (error) {
        console.log(error)
        ElMessage({
            message: "举报失败",
            type: "error"
        })
    }
}

const likeclick = async () => {
    let response
    let token = localStorage.getItem('token')
    try {
        response = await axios.post("/api/forum/post/like?postid=" + postid, {}, {
            headers: {
                'token': `${token}`,
            }
        });
        if (response.status == 200) {
            if (isliked.value) {
                isliked.value = false;
                post.value.likes--;
            } else {
                isliked.value = true;
                post.value.likes++;
            }
        }
    } catch (error) {
        console.log(error)
        ElMessage({
            message: isliked ? "取消点赞失败" : "点赞失败",
            type: "error"
        })
    }
}
const collectclick = async () => {
    let response
    let token = localStorage.getItem('token')
    try {
        response = await axios.post("/api/forum/post/collect?postid=" + postid, {}, {
            headers: {
                'token': `${token}`,
            }
        });
        if (response.status == 200) {
            if (iscollected.value) {
                iscollected.value = false;
                post.value.collect--;
            } else {
                iscollected.value = true;
                post.value.collect++;
            }
        }
    } catch (error) {
        console.log(error)
        ElMessage({
            message: iscollected ? "取消收藏失败" : "收藏失败",
            type: "error"
        })
    }
}
const commentclick = async () => {
    let response
    let token = localStorage.getItem('token')
    try {
        response = await axios.post("/api/forum/post/comment", {
            "postid": postid,
            "comment": newcomment.value,
        },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'token': `${token}`,
                }
            });
        if (response.status == 200) {
            ElMessage({
                message: "评论成功",
                type: "success"
            })
            loadPost(postid)
        }
    } catch (error) {
        console.log(error)
        ElMessage({
            message: "评论失败",
            type: "error"
        })
    }
}
// const convertFileToImageUrl = (file) => {
//     if (file) {
//         return "http://localhost:8080/" + file;
//     }
//     return '';
// };
</script>

<style lang="scss" scoped>
#QC-bg {
    width: 90%;
    background-color: #fff;
    max-width: 1200px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    margin-top: 10px;
    margin-left: 50px;
    padding-left: 30px;
    margin-bottom: 20px;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
    // border: 1px solid #000;
}

#Box {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    padding: 10px;
    // background-color: #efefef80;
}

.QC-title {
    font-size: large;
    font-weight: bold;
}

#newcomment {
    display: flex;
    justify-content: flex-start;
    width: 90%;
    background-color: #fff;
    max-width: 1200px;
    // box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
    margin-left: 50px;
    padding-left: 30px;
    margin-bottom: 20px;
}
</style> 
