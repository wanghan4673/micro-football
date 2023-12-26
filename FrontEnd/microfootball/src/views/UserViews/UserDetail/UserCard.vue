<!-- 个人名片 -->
<template>
    <div class="user-card-box">
        <div class="user-avator-box">
            <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" class="user-avator"
                alt="Avatar"></el-avatar>
        </div>
        <div class="user-center-box">
            <div class="username-box">
                用户名
            </div>
            <div class="signature-box">
                <p class="signature-detail">
                    个性签名
                </p>
            </div>
        </div>
        <div class="user-right-box">
            <div class="number-detail" style="cursor: pointer;" @click="toMyFollow()">
                关注数: {{ followNum }}
            </div>
            <div class="number-detail" style="cursor: pointer;" @click="toMyFans()">
                粉丝数: {{ fansNum }}
            </div>
            <div class="number-detail">
                积分总量: {{ score }}
            </div>
            <div class="edit-profile-button" @click="toEditProfile()">
                修改个人资料
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted,defineEmits } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const score = ref<number>(0)
const followNum = ref<number>(0)
const fansNum = ref<number>(0)
const edmitEvents = defineEmits(['son-click'])  // 向父组件传值
const isPost = ref(true)
const isFans = ref(false)
const isFollow = ref(false)


onMounted(()=>{
    getScore()
    getNums()
})

const getScore = async () => {
    const token = localStorage.getItem('token')
    try{
        const response = await axios.get('/api/user/score',{
            headers: {
                'token': token,
            }
        })
        if (response.data.code == 1) {
            score.value = response.data.data
        } else {
            ElMessage({
                message: '获取积分失败!',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '获取积分请求发送失败',
            type: 'error',
        })
    }
}

const getNums = async () => {
    const token = localStorage.getItem('token')
    try{
        const response = await axios.get('/api/user/followCount',{
            headers: {
                'token': token,
            }
        })
        if (response.data.code == 1) {
            followNum.value = response.data.data[0]?.follow || 0
            fansNum.value = response.data.data[0]?.fans || 0
        } else {
            ElMessage({
                message: '获取关注数失败!',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '获取关注数请求发送失败',
            type: 'error',
        })
    }
}

const toMyFollow = () => {
    isFans.value = false
    isFollow.value = true
    isPost.value = false
    edmitEvents('son-click',isPost.value,isFans.value,isFollow.value)
}
const toMyFans = () => {
    isFans.value = true
    isFollow.value = false
    isPost.value = false
    edmitEvents('son-click',isPost.value,isFans.value,isFollow.value)
}
const toEditProfile = () => {

}
</script>

<style lang="scss">
@import '../../../assets/css/frame.scss';

.user-card-box {
    // 名片
    @extend .grid-three-container;
    height: 20vh;
    background-color: #c4e7fe;
    border-radius: 1rem;
}

.user-avator-box {
    // 头像居中
    display: flex;
    justify-content: center;
    align-items: center;
}

.user-avator {
    // 头像大小
    width: 6rem;
    height: 6rem;
}

.username-box {
    // 用户名部分
    height: 2rem;
    font-size: 2rem;
    margin-bottom: 1rem;
}

.signature-box {
    // 签名部分
    height: 5rem;
    font-size: 1rem;

    .signature-detail {
        // 只展示两行
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
    }
}

.user-right-box {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: start;
}

.number-detail {
    margin-bottom: 0.2rem;
    font-size: 0.8rem;
}

.edit-profile-button {
    color: blue;
    font-size: 0.8rem;
    margin-top: 1rem;
    width: 6rem;
    transition: transform 0.3s ease;
    &:hover{
        color: red;
        transform: scale(1.1);
        cursor: pointer;
    }
}
</style>