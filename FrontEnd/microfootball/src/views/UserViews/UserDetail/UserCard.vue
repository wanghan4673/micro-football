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
                关注数:1
            </div>
            <div class="number-detail" style="cursor: pointer;" @click="toMyFans()">
                粉丝数:1
            </div>
            <div class="number-detail">
                积分总量:1
            </div>
            <div class="edit-profile-button" @click="toEditProfile()">
                修改个人资料
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">

const toMyFollow = () => {

}
const toMyFans = () => {

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
    background-color: #00ffff;
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