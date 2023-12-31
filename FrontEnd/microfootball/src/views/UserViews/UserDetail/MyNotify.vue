<template>
    <div class="block text-center" m="t-4">
        <el-carousel trigger="click" height="150px" indicator-position="none" arrow="always">
            <el-carousel-item v-for="(item, index) in notifyList" :key="index" :class="'item-' + (index % 3)">
                <h3 class="small justify-center" text="2xl">{{ item.title }}</h3>
                <h4 class="notify-detail">{{ item.detail }}</h4>
                <h5 class="notify-time">{{ item.time }}</h5>
            </el-carousel-item>
        </el-carousel>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const notifyList = ref<{ title: string; detail: string; time: string; }[]>([])

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
        if (response.data.code === 1) {
            notifyList.value = response.data.data.map((item: { title: string; detail: string; time: string }) => ({
                title: item.title,
                detail: item.detail,
                time: item.time.split('T')[0]
            }))
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

<style lang="scss">
.el-carousel__item h3 {
    color: #475669;
    margin: 0;
    text-align: center;
}

.el-carousel__item.item-0 {
    background-color: #99a9bf;
}

.el-carousel__item.item-1 {
    background-color: #7fa3cc;
}

.el-carousel__item.item-2 {
    background-color: #d3dce6;
}

.notify-detail {
    color: #475669;
    width: 90%;
    margin: 0 auto;
}

.notify-time {
    color: #475669;
    position: absolute;
    bottom: 5px;
    right: 5px;
}
</style>
