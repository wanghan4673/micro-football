<template>
    <el-header>
        <div class="nav-container">
            <div class="nav-left">
                <div class="nav-logo">
                    <img src="@/assets/img/tjlogo.png" style="width: 50px;height: 50px;" />
                </div>
                <el-menu mode="horizontal" :ellipsis="false">
                    <el-menu-item index=1 @click="redirectToMain"
                        :class="{ 'blue-text': menutextcolor === 2 }">首页</el-menu-item>
                    <el-menu-item index=2 @click="redirectToNews"
                        :class="{ 'blue-text': menutextcolor === 3 }">新闻</el-menu-item>
                    <el-menu-item index=3 @click="redirectToForum"
                        :class="{ 'blue-text': menutextcolor === 4 }">论坛</el-menu-item>
                    <el-menu-item index=4 @click="redirectToIM"
                        :class="{ 'blue-text': menutextcolor === 5 }">聊天</el-menu-item>
                    <el-menu-item index=5 @click="redirectToGames"
                        :class="{ 'blue-text': menutextcolor === 6 }">赛事</el-menu-item>
                    <el-menu-item index=6 @click="redirectToPlayers"
                        :class="{ 'blue-text': menutextcolor === 7 }">球员信息</el-menu-item>

                </el-menu>
            </div>
            <div class="nav-right">
                <el-dropdown trigger="hover">
                    <el-avatar :src=this.avatarurl alt="Avatar" class="avatar"></el-avatar>
                    <template #dropdown>
                        <el-dropdown-menu v-slot: dropdown>
                            <el-dropdown-item @click="redirectToLogin(0)" v-if="!this.islog">用户登录/注册</el-dropdown-item>
                            <el-dropdown-item @click="redirectToLogin(1)" v-if="!this.islog">管理员登录</el-dropdown-item>
                            <el-dropdown-item @click="redirectToPersonal" v-if="this.islog">个人中心</el-dropdown-item>
                            <el-dropdown-item @click="logout" v-if="this.islog">登出</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
                <span class="user-nickname">{{ this.userName }}</span>
            </div>
        </div>
    </el-header>
</template>
    
  
<script>
import axios from 'axios';
import { ElMessage } from 'element-plus';
// import { loginStore } from '../stores/login';
export default {
    data() {
        return {
            islog: false,
            avatarurl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
            userName: '',
        }
    },
    mounted() {
        this.JudgeAccount();
    },
    computed: {
        menutextcolor() {
            if (this.$route.path === '/') {
                return 2;
            } else if (this.$route.path === '/News') {
                return 3;
            } else if (this.$route.path === '/forum') {
                return 4;
            } else if (this.$route.path === '/') {
                return 5;
            } else if (this.$route.path === '/Games') {
                return 6;
            } else if (this.$route.path === '/Players') {
                return 7;
            }
        },
    },
    methods: {
        async JudgeAccount() {
            const token = localStorage.getItem('token');
            let response
            try {
                const response = await axios.get('/api/user/loginStatus', {
                    headers: {
                        'token': token
                    }
                })
                if (response.data.code == 1 && response.data.data != null) {
                    this.islog = true;
                    this.userName = response.data.data.name;
                    this.avatarurl = response.data.data.avatar;
                }
                else if (response.data.code == 1 && response.data.data == null) {
                    this.islog = false;
                }
            } catch (err) {
                if (err.response && err.response.status === 401) {
                    // token非法 登录状态异常
                    ElMessage({
                        message: 'token非法,登录状态异常',
                        grouping: false,
                        type: 'error',
                    })
                }
            }
        },
        redirectToLogin(isAdmin) {
            // if (isAdmin) {
            //     const store = loginStore()
            //     store.adminLogin()
            // } else {
            //     const store = loginStore()
            //     store.userLogin()
            // }
            // 跳转到用户登录页面的逻辑
            if(isAdmin == 0){
                this.$router.push('/signin')
            }
        },
        redirectToForum() {
            //跳转到论坛页面的逻辑
            this.$router.push('/forum')
        },
        redirectToIM() {
            //跳转到聊天页面的逻辑
            this.$router.push('/IM')
        },
        redirectToMain() {
            //跳转到管理员首页页面的逻辑
            this.$router.push('/adminHome')
        },
        redirectToPersonal() {
            //跳转到个人中心页面的逻辑
            this.$router.push('/personal')
        },
        redirectToGames() {
            //跳转到赛事页面的逻辑
            this.$router.push('/Games')
        },
        redirectToNews() {
            //跳转到新闻页面的逻辑
            this.$router.push('/News')
        },
        redirectToPlayers() {
            //跳转到新闻页面的逻辑
            this.$router.push('/Players')
        },
        logout() {
            localStorage.removeItem('token');
            this.$router.push('/');
            setTimeout(() => {
                window.location.reload(); // 刷新当前页面
            }, 100); // 2000毫秒后刷新
        }
    }
};
</script>
<style scoped>
.nav-container {
    /* margin-top: -10px; */
    /* padding-top: 10px ; */
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 60px;
    width: 100%;
    /* padding: 0 20px; */
    background-color: #fff;
}

.nav-left {
    display: flex;
    align-items: center;
}

.nav-logo {
    font-size: 20px;
    font-weight: bold;
    margin-right: 20px;
}

.nav-right {
    display: flex;
    align-items: center;
}


.avatar {
    width: 50px;
    height: 50px;
    border-radius: 48px;
    margin-right: 10px;
    object-fit: cover;
}

.user-nickname {
    margin-right: 20px;
}

.blue-text {
    color: #3ba7ea;
}
</style>
    