<template>
    <div class="signin-layout">
        <div class="back-container">
            <BackButton />
        </div>
        <div class="signin-container">
            <div class="signin-box">
                <div class="title-box">
                    管理员登录
                </div>
                <div class="input-box">
                    <el-form :model="loginForm">
                        <el-form-item style="margin-bottom: 15px">
                            <el-input style="width:250px" v-model="loginForm.email" placeholder="请输入邮箱"></el-input>
                            <el-button style="margin-left: 2vw;" v-if="isAble" type="primary" @click="getPassword">发送验证码</el-button>
                            <el-button style="margin-left: 2vw;" type="primary" disabled v-else>验证码已发送</el-button>
                        </el-form-item>
                        <el-form-item>
                            <el-input type="password" v-model="loginForm.password" show-password
                                placeholder="请输入验证码"></el-input>
                        </el-form-item>
                    </el-form>
                </div>
                <div class="button-box">
                    <el-button type="primary" class="login-button" @click="signin()">登录</el-button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import { ElMessage} from 'element-plus';
export default {
    components: {
        
    },
    data() {
        return {
            loginForm:{
                email:"",
                password:"",
            },
            isAble:true,
        }
    },
    methods: {
        async getPassword(){
            try {
                const response = await axios.post('/api/admin/login/getCode', {
                        email:this.loginForm.email,
                });
                console.log(response)
                if (response.data.code == 1) {
                    ElMessage({
                        message: '验证码已发送，有效时间五分钟',
                        type: 'success',
                    })
                    this.isAble=false
                }
            } catch (e) {
                ElMessage({
                    message: '管理员邮箱不正确，请重新登陆',
                    type: 'error',
                })
                setTimeout(() => {
                    window.location.reload(); // 刷新当前页面
                }, 1000);
            }
        },
        async signin(){
            try {
                const response = await axios.post('/api/admin/login/checkCode', {
                    email: this.loginForm.email,
                    code: this.loginForm.password,
                });
                console.log(response)
                if (response.data.code == 1) {
                    localStorage.setItem('adminToken', response.data.data)
                    this.$router.push('/adminhome')
                }
            } catch (e) {
                console.log(e)
            }
        },
    }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/frame.scss';
@import '../UserViews/UserCSS/signin.scss';

.signin-container {
    @extend .center-box;
    width: 45%;
    height: 55%;
    border-radius: 8px;
    box-shadow: 2px 1px 19px rgba(0, 0, 0, .3);
}

.signin-box {
    /* 登录注册部分 */
    width: 100%;
    padding-top: 10vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
}

.title-box {
    font-size: 4vh;
    margin-top: 2vh;
    margin-bottom: 2vh;
    align-self: center;
}

.input-box {
    align-self: center;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
}

.button-box {
    display: flex;
    justify-content: center;
}

.login-button {
    margin-top: 2vh;
    width: 40%;
    height: 5vh;
    border-radius: 15px;
    font-weight: bold;

    &:hover {
        transform: scale(1.05);
    }
}
</style>