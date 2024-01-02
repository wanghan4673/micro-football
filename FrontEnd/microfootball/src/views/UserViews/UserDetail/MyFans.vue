<!-- 查看粉丝列表 -->
<template>
    <div class="user-fans-box">
        <el-scrollbar max-height="70vh">
            <div class="back-myPost-button">
                <el-icon class="back-myPost-icon" @click="toMyPost()">
                    <Close />
                </el-icon>
            </div>
            <div v-for="fan, index in fans" :key="fan.id" class="fan-card"
                :style="{ marginBottom: isLastFan(index) ? '0' : '0.5rem' }">
                <div class="fan-name">{{ fan.name }}</div>
                <div class="fan-signature">{{ fan.signature }}</div>
                <el-divider v-if="!isLastFan(index)" />
            </div>
            <el-empty v-if="fans.length === 0" description="你还没有粉丝哦" />
        </el-scrollbar>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, defineEmits } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'
import { gotoPath } from '@/assets/api';

const fans = ref<{ id: number; name: string; signature: string }[]>([])
const edmitEvents = defineEmits(['son-click'])  // 向父组件传值

onMounted(() => {
    getMyFans()
})

const getMyFans = async () => {
    const token = localStorage.getItem('token')
    try {
        const response = await axios.get('/api/users/fans-list', {
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
            console.log("fans: " + fans)
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

const toMyPost = () => {
    const isPost = ref(true)
    const isFans = ref(false)
    const isFollow = ref(false)
    edmitEvents('son-click', isPost.value, isFans.value, isFollow.value)
}
</script>

<style lang="scss">
.back-myPost-button {
    display: flex;
    justify-content: flex-end;
    align-items: center;

    .back-myPost-icon {
        cursor: pointer;
        font-size: 3vh;
    }
}

.user-fans-box {
    height: 70vh;
    border-top-right-radius: 2vh;
    border-top-left-radius: 2vh;
    background: linear-gradient(to bottom, #ffffff, #e8e8e8);
}

.fan-card {
    height: 10vh;
    display: flex;
    flex-direction: column;

    .fan-name {
        display: flex;
        align-items: start;
        font-size: 1.3rem;
        margin-bottom: 1vh;
    }

    .fan-signature {
        width: 95%;
        align-self: center;
    }
}
</style>