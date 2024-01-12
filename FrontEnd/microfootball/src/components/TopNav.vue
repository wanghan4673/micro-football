<template>
    <el-header>
        <div class="nav-container">
            <div class="nav-left">
                <div class="nav-logo">
                    <img src="@/assets/img/tjlogo.png" alt="Logo" style="width: 50px;height: 50px;" />
                </div>
                <el-menu mode="horizontal" :ellipsis="false">
                    <el-menu-item index="1" @click="redirectToNews"
                        :class="{ 'blue-text': menutextcolor === 2 }">新闻</el-menu-item>
                    <el-menu-item index="2" @click="redirectToForum"
                        :class="{ 'blue-text': menutextcolor === 3 }">论坛</el-menu-item>
                    <el-menu-item index="4" @click="redirectToGames"
                        :class="{ 'blue-text': menutextcolor === 5 }">赛事</el-menu-item>
                    <el-menu-item index="5" @click="redirectToPlayers"
                        :class="{ 'blue-text': menutextcolor === 6 }">球员信息</el-menu-item>
                    <el-menu-item index=7 @click="redirectToTeam"
                        :class="{ 'blue-text': menutextcolor === 7 }">球队信息</el-menu-item>
                </el-menu>
            </div>
            <div class="nav-right">
                <el-dropdown trigger="hover">
                    <el-avatar :src="avatarurl" alt="Avatar" class="avatar"></el-avatar>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item @click="redirectToLogin(0)" v-if="!islog">用户登录/注册</el-dropdown-item>
                            <el-dropdown-item @click="redirectToLogin(1)" v-if="!islog">管理员登录</el-dropdown-item>
                            <el-dropdown-item @click="redirectToPersonal" v-if="islog">个人中心</el-dropdown-item>
                            <el-dropdown-item @click="logout" v-if="islog">登出</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
                <span class="user-nickname">{{ userName }}</span>
            </div>
        </div>
    </el-header>
</template>
    
  
<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const islog = ref(false)
const avatarurl = ref("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png")
const userName = ref('')
const router = useRouter()

const menutextcolor = computed(() => {
    const path = router.currentRoute.value.path;
    switch (path) {
        case '/News': return 2;
        case '/forum': return 3;
        case '/IM': return 4;
        case '/Games': return 5;
        case '/Players': return 6;
        case '/Team': return 7;
        default: return 0;
    }
})

onMounted(() => {
    JudgeAccount()
})

const JudgeAccount = async () => {
    const token = localStorage.getItem('token');
    let response
    try {
        const response = await axios.get('/api/users/login-status', {
            headers: {
                'token': token
            }
        })
        if (response.data.code == 1 && response.data.data != null) {
            islog.value = true;
            userName.value = response.data.data.name;
            avatarurl.value = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";
        }
        else if (response.data.code == 1 && response.data.data == null) {
            islog.value = false;
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
}

const redirectToForum = () => {
    router.push('/forum');
}

const redirectToMain = () => {
    router.push('/');
}

const redirectToPersonal = () => {
    router.push('/personal');
}

const redirectToGames = () => {
    router.push('/Games');
}

const redirectToNews = () => {
    router.push('/News');
}

const redirectToPlayers = () => {
    router.push('/Players');
}

const redirectToTeam = () => {
    router.push('/Team')
}

const logout = () => {
    localStorage.removeItem('token')
    sessionStorage.removeItem('gameDialogShown')
    ElMessage({
        message: '登出成功',
        grouping: false,
        type: 'success',
    })
    setTimeout(() => {
        window.location.reload()
    }, 100)
    router.push('/forum')
}

const redirectToLogin = (isAdmin) => {
    if (isAdmin === 0) {
        router.push('/signin');
    } else {
        router.push('/adminsignin');
    }
}
</script>

<style scoped>
.nav-container {
    /* margin-top: -10px; */
    /* padding-top: 10px ; */
    display: flex;
    justify-content: space-between;
    align-items: center;
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
}</style>
    