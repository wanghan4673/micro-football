<!-- 查看我的帖子 -->
<template>
    <div class="user-post-box">
        <el-scrollbar max-height="70vh">
            <div v-for="post, index in posts" :key="post.id" class="post-card" @click="toPost(post.id)"
                :style="{ marginBottom: isLastPost(index) ? '0' : '0.5rem' }">
                <div class="post-title">{{ post.title }}</div>
                <div class="post-content" v-html="post.content"></div>
                <div class="post-time">{{ post.time }}</div>
            </div>
            <el-empty v-if="posts.length === 0" description="你还没有发过帖子,去论坛发帖吧!" />
        </el-scrollbar>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'
import { useRouter } from 'vue-router'

const posts = ref<{ id: number; title: string; content: string; time: string; }[]>([])
const router = useRouter()

onMounted(() => {
    getMyPosts();
})

const getMyPosts = async () => {
    const token = localStorage.getItem('token')
    try {
        const response = await axios.get('/api/users/posts', {
            headers: {
                'token': token,
            }
        })
        if (response.data.code == 1) {
            posts.value = response.data.data.map((item: { id: number; title: string; content: string; time: string }) => ({
                id: item.id,
                title: item.title,
                content: item.content,
                time: item.time.split(' ')[0]
            }))

        } else {
            ElMessage({
                message: '获取帖子失败!',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '获取我的帖子请求发送失败',
            type: 'error',
        })
    }
}

const toPost = (id: number) => {
    router.push({
        path:'/forum/post',
        query:{
            id: id
        }
    })
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
    background-color: #f7f7f7;

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

    .post-time {
        align-self: flex-end;
        margin-right: 0.2rem;
    }
}
</style>