<!-- 查看关于我的动态 -->
<template>
    <div>
        查看关于我的通知
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

onMounted(() => {
    getMyNotify()
})

const getMyNotify = async () => {
    const token = localStorage.getItem('token')
    try {
        const response = await axios.get('/api/user/getNotify', {
            headers: {
                'token': token,
            }
        })
        if (response.data.code == 1) {
            console.log(response.data.data)
        } else {
            ElMessage({
                message: '获取公告失败!',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '获取公告请求发送失败',
            type: 'error',
        })
    }
}
</script>

<style scoped>

</style>