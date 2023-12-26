<!-- 查看关注列表 -->
<template>
    <div class="user-follow-box">
        <el-scrollbar max-height="70vh">
            <div v-for="follow, index in follows" :key="follow.id" class="follow-card"
                :style="{ marginBottom: isLastFollow(index) ? '0' : '0.5rem' }">
                <div class="follow-name">{{ follow.name }}</div>
                <div class="follow-signature">{{ follow.signature }}</div>
            </div>
            <el-empty v-if="follows.length === 0" description="你还没有关注用户哦" />
        </el-scrollbar>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const follows = ref<{ id: number; name: string; signature: string }[]>([])

onMounted(() => {
    getMyFollow()
})

const getMyFollow = async () => {
    const token = localStorage.getItem('token')
    try {
        const response = await axios.get('/api/user/getFollowDetail', {
            headers: {
                'token': token,
            }
        })
        if (response.data.code == 1) {
            console.log(response.data.data)
            follows.value = response.data.data.map((item: { id: number; name: string; signature: string }) => ({
                id: item.id,
                name: item.name,
                signature: item.signature
            }))
        } else {
            ElMessage({
                message: '获取关注列表失败!',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '获取关注列表请求发送失败',
            type: 'error',
        })
    }
}

const isLastFollow = (index: number) => {
    return index === follows.value.length - 1;
}
</script>

<style lang="scss">
.user-follow-box {
    height: 70vh;
    border-top-right-radius: 2vh;
    border-top-left-radius: 2vh;
    background-color: #e8e8e8;
}
</style>