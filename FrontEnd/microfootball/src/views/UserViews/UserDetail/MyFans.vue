<!-- 查看粉丝列表 -->
<template>
    <div class="user-fans-box">
        <el-scrollbar max-height="70vh">
            <div v-for="fan, index in fans" :key="fan.id" class="follow-card"
                :style="{ marginBottom: isLastFan(index) ? '0' : '0.5rem' }">
                <div class="fan-name">{{ fan.name }}</div>
                <div class="fan-signature">{{ fan.signature }}</div>
            </div>
            <el-empty v-if="fans.length === 0" description="你还没有关注用户哦" />
        </el-scrollbar>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const fans = ref<{ id: number; name: string; signature: string }[]>([])

onMounted(() => {
    getMyFans()
})

const getMyFans = async () => {
    const token = localStorage.getItem('token')
    try {
        const response = await axios.get('/api/user/getFansDetail', {
            headers: {
                'token': token,
            }
        })
        if (response.data.code == 1) {
            fans.value = response.data.data.map((item: { id: number; name: string; signature: string }) => ({
                id: item.id,
                name: item.name,
                signature: item.signature
            }))
            console.log("fans: "+fans)
        } else {
            ElMessage({
                message: '获取粉丝列表失败!',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '获取粉丝列表请求发送失败',
            type: 'error',
        })
    }
}

const isLastFan = (index: number) => {
    return index === fans.value.length - 1;
}
</script>

<style lang="scss">
.user-fans-box {
    height: 70vh;
    border-top-right-radius: 2vh;
    border-top-left-radius: 2vh;
    background-color: #e8e8e8;
}
</style>