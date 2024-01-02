<!-- 查看关注列表 -->
<template>
    <div class="user-follow-box">
        <el-scrollbar max-height="70vh">
            <div class="back-myPost-button">
                <el-icon class="back-myPost-icon" @click="toMyPost()">
                    <Close />
                </el-icon>
            </div>
            <div v-for="follow, index in follows" :key="follow.id" class="follow-card"
                :style="{ marginBottom: isLastFollow(index) ? '0' : '0.5rem' }">
                <div class="follow-content">
                    <div class="follow-name">{{ follow.name }}</div>
                    <div class="follow-signature">{{ follow.signature }}</div>
                </div>
                <el-button class="cancel-follow-btn" @click="deleteFollow(follow.id)">取消关注</el-button>
                <el-divider v-if="!isLastFollow(index)" />
            </div>
            <el-empty v-if="follows.length === 0" description="你还没有关注用户哦" />
        </el-scrollbar>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, defineEmits } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const follows = ref<{ id: number; name: string; signature: string }[]>([])
const edmitEvents = defineEmits(['son-click'])  // 向父组件传值

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

const deleteFollow = async (deleteId: number) => {
    const token = localStorage.getItem('token')
    try {
        const response = await axios.delete(`/api/user/deleteFollow/${deleteId}`, {
            headers: {
                'token': token,
            }
        })
        console.log(response)
        if (response.data.code == 1) {
            ElMessage({
                message: '取消关注成功!',
                type: 'success',
            })
            setTimeout(() => {
                window.location.reload()
            }, 500)
        } else {
            ElMessage({
                message: '取消关注失败!',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '取消关注请求发送失败',
            type: 'error',
        })
    }
}

const isLastFollow = (index: number) => {
    return index === follows.value.length - 1;
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

.user-follow-box {
    height: 70vh;
    border-top-right-radius: 2vh;
    border-top-left-radius: 2vh;
    background: linear-gradient(to bottom, #ffffff, #e8e8e8);
}

.follow-card {
    height: 10vh;
    display: flex;
    flex-direction: column;
    position: relative;

    .follow-name {
        align-items: start;
        font-size: 1.3rem;
        margin-bottom: 1vh;
    }

    .follow-signature {
        width: 95%;
        align-self: center;
    }

    .cancel-follow-btn {
        position: absolute;
        top: 50%;
        right: 5%;
        transform: translateY(-50%);
    }
}
</style>