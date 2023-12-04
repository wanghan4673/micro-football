<template>
    <div class="signin-layout">
        <div class="back-container">
            <BackButton />
        </div>
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
                        <el-form :model="registerForm" :rules="rules" status-icon>
                            <el-form-item style="margin-bottom: 10px" prop="username">
                                <el-tooltip effect="light" content="用户名不可超过10个字符" placement="right">
                                    <el-input style="width:250px" v-model="registerForm.username"
                                        placeholder="用户名"></el-input>
                                </el-tooltip>
                            </el-form-item>
                            <el-form-item style="margin-bottom: 10px" prop="account">
                                <el-tooltip effect="light" content="账号仅由数字组成,且不超过10位" placement="right">
                                    <el-input v-model="registerForm.account" placeholder="账号"></el-input>
                                </el-tooltip>
                            </el-form-item>
                            <el-form-item style="margin-bottom: 10px" prop="password">
                                <el-tooltip effect="light" content="密码只能由数字和字母组成,且不超过12位" placement="right">
                                    <el-input type="password" v-model="registerForm.password" show-password
                                        placeholder="密码"></el-input>
                                </el-tooltip>
                            </el-form-item>
                            <el-form-item prop="confirmPassword" validator="validatePassword">
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
                                <el-input style="width:250px" v-model="loginForm.account" placeholder="请输入账号"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-input type="password" v-model="loginForm.password" show-password
                                    placeholder="请输入密码"></el-input>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div class="button-box">
                        <el-button type="primary" class="login-button" @click="login()">登录</el-button>
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
import { ref, reactive } from 'vue'
import axios from 'axios'
import { ElMessage, type FormRules } from 'element-plus'
import BackButton from '../../components/BackButton.vue'

interface RuleForm {
    username: string
    account: string
    password: string
}

const isRegister = ref(false)  // 当前是否为注册页面 为true则注册 为false则登录
const loginForm = ref({
    account: '',
    password: '',
})  // 登录表单
const registerForm = ref({
    username: '',
    account: '',
    password: '',
    confirmPassword: '',
})  // 注册表单

const rules = reactive<FormRules<RuleForm>>({
    // 定义表单规则(element-plus内置功能) 二次确认密码将在注册前提示
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { max: 10, message: '用户名不可超过10个字符', trigger: 'blur' }
    ],
    account: [
        { required: true, message: '请输入账号', trigger: 'blur' },
        { pattern: /^\d+$/, message: '账号仅由数字组成', trigger: 'blur' },
        { max: 10, message: '账号不可超过10位', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { pattern: /^[a-zA-Z0-9]+$/, message: '密码只能由数字和字母组成', trigger: 'blur' },
        { max: 12, message: '密码不可超过12位', trigger: 'blur' }
    ]
})

const changeScene = () => {
    isRegister.value = !isRegister.value
}

const login = async () => {
    let response
    try {
        response = await axios.post('/api/user/login', {
            account: loginForm.value.account,
            password: loginForm.value.password
        })
    } catch (error) {
        ElMessage({
            message: '登录请求发送失败',
            type: 'error',
        })
    }
    console.log("login response: ", response)
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

.login-box,
.register-box {
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