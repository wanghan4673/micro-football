<template>
    <div class="signin-layout">
        <div class="signin-container">
            <div class="bg-box"
                :class="{ 'slide-to-left': isRegister, 'slide-to-right': !isRegister, 'left-bg': isRegister, 'right-bg': !isRegister }">
            </div>
            <div class="signin-box" :class="{ 'right-position': isRegister, 'left-position': !isRegister }">
                <div class="register-box" v-if="isRegister">
                    <div class="title-box">
                        注册
                    </div>
                    <div class="input-box">
                        <el-form :model="registerForm">
                            <el-form-item style="margin-bottom: 10px">
                                <el-tooltip effect="light" content="用户名不可超过10个字符" placement="right">
                                    <el-input style="width:200px" v-model="registerForm.username"
                                        placeholder="用户名"></el-input>
                                </el-tooltip>
                            </el-form-item>
                            <el-form-item style="margin-bottom: 10px">
                                <el-tooltip effect="light" content="账号仅由数字组成,且不超过10位" placement="right">
                                    <el-input v-model="registerForm.account" placeholder="账号"></el-input>
                                </el-tooltip>
                            </el-form-item>
                            <el-form-item style="margin-bottom: 10px">
                                <el-tooltip effect="light" content="密码只能由数字和字母组成,且不超过12位" placement="right">
                                    <el-input type="password" v-model="registerForm.password" show-password
                                        placeholder="密码"></el-input>
                                </el-tooltip>
                            </el-form-item>
                            <el-form-item>
                                <el-input type="password" v-model="registerForm.confirmPassword" show-password
                                    placeholder="再次输入密码"></el-input>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div class="button-box">
                        <el-button type="primary" class="register-button">注册</el-button>
                    </div>
                    <div class="button-box">
                        <el-button class="jump-button" type="text" @click="changeScene">已有账号?点此登录</el-button>
                    </div>
                </div>
                <div class="login-box" v-else>
                    <div class="title-box">
                        登录
                    </div>
                    <div class="input-box">
                        <el-form :model="loginForm">
                            <el-form-item style="margin-bottom: 15px">
                                <el-input style="width:200px" v-model="loginForm.account" placeholder="请输入账号"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-input type="password" v-model="loginForm.password" show-password
                                    placeholder="请输入密码"></el-input>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div class="button-box">
                        <el-button type="primary" class="login-button">登录</el-button>
                    </div>
                    <div class="button-box">
                        <el-button class="jump-button" type="text" @click="changeScene">还没有账号?点此注册</el-button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
const isRegister = ref(false)  // 当前是否为注册页面 为true则注册 为false则登录
const loginForm = {
    account: '',
    password: '',
}  // 登录表单
const registerForm = {
    username: '',
    account: '',
    password: '',
    confirmPassword: '',
}

const changeScene = () => {
    isRegister.value = !isRegister.value
}
</script>

<style lang="scss">
@import '../../assets/css/frame.scss';
@import '../../assets/css/signin.scss';

.signin-container {
    @extend .center-box;
    width: 45%;
    height: 55%;
    border-radius: 8px;
    box-shadow: 2px 1px 19px rgba(0, 0, 0, .3);
}

.bg-box {
    width: 50%;
    height: 100%;
    left: 50%;
    position: absolute;
    /* 动画过渡 */
    transition: 0.5s ease-in-out;
    /* 置于上方 使得滑动时不突兀 */
    z-index: 99;
}


.slide-to-left {
    /* 滑动到左侧 */
    transform: translateX(-100%);
}

.slide-to-right {
    /* 滑动到右侧 */
    transform: translateX(0);
}

.signin-box {
    /* 登录注册部分 */
    width: 50%;
}

.left-position {
    transform: translateX(0);
}

.right-position {
    transform: translateX(100%);
}

.login-box,.register-box{
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.title-box {
    font-size: 4vh;
    margin-top: 2vh;
    margin-bottom: 2vh;
    align-self: center;
}

.input-box {
    align-self: center;
}

.button-box {
    display: flex;
    justify-content: center;
}

.login-button,
.register-button {
    margin-top: 2vh;
    width: 40%;
    height: 5vh;
    border-radius: 15px;
    font-weight: bold;
    &:hover {
        transform: scale(1.05);
    }
}

.jump-button {
    margin-top: 1vh;
    width: 45%;
    color: #274046;
    &:hover {
        color: #ee9ca7 !important;
        transform: scale(1.05);
    }
}
</style>