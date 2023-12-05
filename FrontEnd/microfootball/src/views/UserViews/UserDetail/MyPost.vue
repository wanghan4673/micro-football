<!-- 查看我的帖子 -->
<template>
    <div class="user-post-box">
        <el-scrollbar max-height="70vh">
            <div v-for="post, index in posts" :key="post.id" class="post-card" @click="toPost(post.id)"
                :style="{ marginBottom: isLastPost(index) ? '0' : '0.5rem' }">
                <div class="post-title">{{ post.title }}</div>
                <div class="post-content">{{ post.content }}</div>
                <div class="post-time">{{ post.time }}</div>
            </div>
        </el-scrollbar>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const posts = ref<{ id: number; title: string; content: string; time: string; }[]>([
    { id: 1, title: 'Post 1', content: 'Content for post 1', time: '2023-01-01' },
    { id: 2, title: 'Post 2', content: 'Content for post 2', time: '2023-01-02' },
    { id: 3, title: 'Post 3', content: 'Content for post 3', time: '2023-01-03' },
    { id: 4, title: 'Post 4', content: 'Content for post 4', time: '2023-01-04' },
    { id: 5, title: 'Post 5', content: 'Content for post 5', time: '2023-01-05' },
    { id: 6, title: 'Post 6', content: 'Content for post 6', time: '2023-01-06' },
    { id: 7, title: 'Post 7', content: 'Content for post 7', time: '2023-01-07' },
    { id: 8, title: 'Post 8', content: 'Content for post 8', time: '2023-01-08' },
    { id: 9, title: 'Post 9', content: 'Content for post 9', time: '2023-01-09' },
    { id: 10, title: 'Post 10', content: 'Content for post 10', time: '2023-01-10' }
])

const toPost = (id: number) => {

}

const isLastPost = (index: number) => {
    // 最后一个帖子没有margin-bottom
    return index === posts.value.length - 1;
}
</script>

<style lang="scss">
.user-post-box {
    height: 70vh;
    border-top-right-radius: 2vh;
    border-top-left-radius: 2vh;
    background-color: #e8e8e8;
}

.post-card {
    height: 20vh;
    display: flex;
    flex-direction: column;
    background-color: #ffffff;

    .post-title {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 1.5rem;
    }

    .post-content {
        width: 90%;
        align-self: center;
        flex: 1;
    }

    .post-time{
        align-self: flex-end;
        margin-right: 0.2rem;
    }
}
</style>