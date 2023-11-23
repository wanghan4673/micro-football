<template>
    <div style="display: flex;">
        <el-icon @click="back" style="margin-top: 18px; margin-left: 10px;">
            <Back />
        </el-icon>
        <div id="Box" v-if="forumstore.chosedPost.title">
            <p>{{forumstore.chosedPost.title}}</p>
        </div>
    </div>
    <div id="QC-bg">

        <div id="Box" v-if="forumstore.chosedPost.content">
            <p style="width: 60%;" v-html="forumstore.chosedPost.content"></p>
        </div>
        <!-- v-if="props.Question.img && props.Question.img.length > 0"  -->
        <div id="Box" v-if="forumstore.chosedPost.img"> 
            <div v-for="img of forumstore.chosedPost.img" :key="img">
                <img :src="img" alt="图片加载失败" style="max-width: 60%;" />
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineProps } from "vue"
import { useRouter } from "vue-router"
const router = useRouter()
import { useForumStore } from '../stores/forum.ts'
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
    justify-content: felx-start;
    margin-top: 10px;
    margin-bottom: 100px;
    padding-bottom: 50px;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
}

#Box {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: felx-start;
    padding-left: 20px;
}

.QC-title {
    font-size: large;
    font-weight: bold;
}
</style> 
