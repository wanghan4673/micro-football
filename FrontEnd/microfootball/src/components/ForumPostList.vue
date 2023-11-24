<template>
    <div v-for="post in PostList" :key="post">
        <ForumPostItem :post="post" @click="toPost(post)"></ForumPostItem>
    </div>
    <div style="display: flex;justify-content: center;">
        <el-pagination @current-change="handleCurrentChange" background layout="prev, pager, next" :total=totalElements />
    </div>
</template>

<script setup>
import ForumPostItem from "./ForumPostItem.vue"
import {ref} from 'vue'
import { defineProps } from 'vue'
import {useForumStore} from '../stores/forum.ts'
const forumstore = useForumStore()
import { useRouter } from 'vue-router';
const router = useRouter()

const toPost = (post) =>{
    forumstore.chosedPost = post
    router.push({
        path: '/forum/post',
        // query: {
        //     // _id: post._id
        // },
    })
}   
let Post1 = {
    title: "Post 1",
    img: [require("@/assets/img/carousel2.png")],
    content: "PostPost 1wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwPost 1wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwPost 1wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
    comments: ['eawdwadawdadwadwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwdddddddddddddddddddaaaaaaaaaawwwwwwwwwwwwwsssssssssssssawee','ccc'],
}
let Post2 = {
    title: "Post 2",
    img: [require("@/assets/img/carousel2.png")],
    content: "Post 2\nPost 2",
    comments: ['eee','ccc'],
}
let Post3 = {
    title: "Post 3",
    img: [require("@/assets/img/carousel2.png")],
    content: "Post 3\nPost 3",
    comments: ['eee','ccc'],
}
let Post4 = {
    title: "Post 4",
    img: [require("@/assets/img/carousel2.png")],
    content: "Post 4\nPost 4",
    comments: ['eee','ccc'],
}

let PostList =ref([Post1,Post2,Post3,Post4,Post1,Post2,Post3,Post4,Post1,Post2,Post3,Post4,Post1,Post2,Post3,Post4])
let totalElements = ref(PostList.value.length)

const handleCurrentChange = async (newPage) => {
    PostList.value =[Post1,Post2,Post3]
}
</script>

<style lang="scss" scoped>

</style>