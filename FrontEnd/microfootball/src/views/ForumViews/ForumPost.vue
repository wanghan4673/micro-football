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
        <!-- <div class="approval-collect">
            <el-icon>
                <CircleCheck />
            </el-icon>
            <span class="post-approval">{{ post_approval[index] }}</span>
            <el-icon>
                <Star />
            </el-icon>
            <span>{{ post_collect[index] }}</span>
        </div> -->
    </div>
    <p style="font-size: large; font-weight: 400; margin: 20px;margin-left: 50px;">评论 {{ comments ? comments.length : 0 }}</p>
    <div id="QC-bg">
        <div id="Box" style="height: 7vh;padding-top: 20px;"  v-for="(comment,index) of comments" :key="index">
            <div style="width: 100%; display:flex; justify-content:space-between">
                <div style="max-width:70% ; word-wrap: break-word;">{{ comment.comment }}</div>
                <div style="max-width: 25%; display: flex;">
                    <img :src="comment.avatar" alt="头像">
                    <p>{{ comment.name }}</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineProps, onMounted, ref } from "vue"
import { useRouter, useRoute } from "vue-router"
import axios from "axios"
const router = useRouter()
const route = useRoute()
import { useForumStore } from '../../stores/forum.ts'
import { ElMessage } from 'element-plus'
const forumstore = useForumStore()
const back = () => {
    router.back(forumstore.chosedPost);
}
let postid = route.query.id;
let post = ref({ title: null, content: null, img: null })
let comments = ref([])
let poster = ref(null)
onMounted(() => {
    loadPost(postid)
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
        }
    } catch (error) {
        console.log(error)
        ElMessage({
            message: "获取失败",
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
    padding-bottom: 50px;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
}

#Box {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    padding: 10px;
}

.QC-title {
    font-size: large;
    font-weight: bold;
}
</style> 
