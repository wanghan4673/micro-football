<template>
    <div id="postlist">
        <div v-for="post in PostList" :key="post">
            <ForumPostItem :post="post" @click="toPost(post)"></ForumPostItem>
        </div>
        <div style="display: flex;justify-content: center; margin: 20px;">
            <el-pagination @current-change="handleCurrentChange" background layout="prev, pager, next"
                :total=totalElements 
                :current-page="forumstore.chosedPage" />
        </div>
    </div>
</template>

<script setup>
import ForumPostItem from "./ForumPostItem.vue"
import { ref, onMounted, watch } from 'vue'
import { defineProps } from 'vue'
import { useForumStore } from '../../stores/forum.ts'
import { ElMessage } from 'element-plus'
let forumstore = useForumStore()
import { useRouter } from 'vue-router';
import axios from 'axios'
const router = useRouter()

const toPost = (post) => {
    forumstore.chosedPost = post
    router.push({
        path: '/forum/post',
        query: {
            id: post.id
        }
    })
}
let PostList = ref([])
let totalElements = ref(PostList.value.length)
onMounted(() => {
    loadPosts();
})

watch(() => forumstore.getPosts, (newValue, oldValue) => {
    loadPosts();
})
watch(() => forumstore.sorttype, (newValue, oldValue) => {
    loadPosts();
})
watch(() => forumstore.sorttag, (newValue, oldValue) => {
    loadPosts();
})
const handleCurrentChange = async (newPage) => {
    forumstore.chosedPage = newPage
    loadPosts()
}
const loadPosts = async () => {
    let response
    try {
        response = await axios.get('/api/forum?page=' + forumstore.chosedPage + '&size=10&keyword=' + forumstore.keyword + '&tag=' + forumstore.sorttag)
        if (response.status == 200) {
            PostList.value = []
            totalElements = 0
            console.log(response)
            for (const post of response.data.data.posts) {
                PostList.value.push(post)
            }
            totalElements = response.data.data.count;
        }
    } catch (e) {
        console.log(e.message)
        ElMessage({
            message: "获取帖子失败",
            type: "error"
        })
    }
}

</script>

<style lang="scss" scoped>
#postlist {
    height: 86vh;
    overflow-x:hidden;
    overflow-y: auto;
}
#postlist::-webkit-scrollbar {
  	display: none;
}

</style>