<template>
    
    <div style="display: flex; margin-left: 20px;">
        <el-icon @click="back" style="margin-top: 20px; margin-left: 10px;">
            <Back />
        </el-icon>
        <div id="Box" v-if="forumstore.chosedPost.title">
            <p style="font-size: large; font-weight: 400;">{{ forumstore.chosedPost.title }}</p>
        </div>
    </div>
    
    <div id="QC-bg">
        <div id="Box" v-if="forumstore.chosedPost.content">
            <div style="width: 80%;word-wrap: break-word">
                <p v-html="forumstore.chosedPost.content"></p>
            </div>
        </div>
        <!-- v-if="props.Question.img && props.Question.img.length > 0"  -->
        <div id="Box" v-if="forumstore.chosedPost.img">
            <div v-for="img of forumstore.chosedPost.img" :key="img">
                <img :src="img" alt="图片加载失败" style="max-width: 60%;" />
            </div>
        </div>
        <div class="approval-collect">
            <el-icon>
                <CircleCheck />
            </el-icon>
            <!-- <span class="post-approval">{{ post_approval[index] }}</span> -->
            <el-icon>
                <Star />
            </el-icon>
            <!-- <span>{{ post_collect[index] }}</span> -->
        </div>
    </div>
    <p style="font-size: large; font-weight: 400; margin-left: 10px;">评论 {{ forumstore.chosedPost.comments.length }}</p>
    <div id="QC-bg">
        <div id="Box" v-for="comment of forumstore.chosedPost.comments" :key="comment">
            <div style="width: 80%; word-wrap: break-word">
                <p>{{ comment }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineProps } from "vue"
import { useRouter } from "vue-router"
const router = useRouter()
import { useForumStore } from '../../stores/forum.ts'
const forumstore = useForumStore()
const back = () => {
    router.back();
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
    padding-left: 20px;
}

.QC-title {
    font-size: large;
    font-weight: bold;
}
</style> 
