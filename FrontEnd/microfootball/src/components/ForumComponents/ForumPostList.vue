<template>
    <div v-for="post in PostList" :key="post">
        <ForumPostItem :post="post" @click="toPost(post)"></ForumPostItem>
    </div>
    <div style="display: flex;justify-content: center; margin: 20px;">
        <el-pagination @current-change="handleCurrentChange" background layout="prev, pager, next" :total=totalElements />
    </div>
</template>

<script setup>
import ForumPostItem from "./ForumPostItem.vue"
import {ref,onMounted,watch} from 'vue'
import { defineProps } from 'vue'
import {useForumStore} from '../../stores/forum.ts'
import { ElMessage } from 'element-plus'
let forumstore = useForumStore()
import { useRouter } from 'vue-router';
import axios from 'axios'
const router = useRouter()

const toPost = (post) =>{
    forumstore.chosedPost = post
    router.push({
        path: '/forum/post',
        query:{
            id: post.id
        }
    })
}
let PostList =ref([])
let totalElements = ref(PostList.value.length)
let page = 1
onMounted(()=>{
    loadPosts();
})
watch(()=>forumstore.getPosts,(newValue,oldValue)=>{
    loadPosts();
})
watch(()=>forumstore.sorttype,(newValue,oldValue)=>{
    loadPosts();
})
watch(()=>forumstore.sorttag,(newValue,oldValue)=>{
    loadPosts();
})
const handleCurrentChange = async (newPage) => {
    page = newPage + 1
    loadPosts()
}
const loadPosts = async() =>{
    PostList.value = []
    totalElements=0
    let response
    // let timeQ = forumstore.sorttype=='like'?false:true
    try{
        response = await axios.get('/api/forum?page='+page+'&size=10&keyword='+forumstore.keyword+'&tag='+forumstore.sorttag)
        if(response.status == 200){
            for(const post of response.data.data.posts){
                PostList.value.push(post)
            }
            totalElements = response.data.data.count;
        }
    }catch(e){
        console.log(e.message)
        ElMessage({
            message: "获取帖子失败",
            type:"error"
        })
    }
}

</script>

<style lang="scss" scoped>

</style>