<template>
    <div class="grid-three-container">
        <BackWave />
        <div class="user-left-container">
            <div class="notify-container">
                <MyNotify />
            </div>
        </div>
        <div class="user-center-container">
            <div class="usercard-container">
                <UserCard @son-click="changeFromCard" />
            </div>
            <div class="change-container">
                <MyPost v-if="viewPost" />
                <MyFans @son-click="changeFromCard" v-if="viewFans" />
                <MyFollow @son-click="changeFromCard" v-if="viewFollow" />
            </div>
        </div>
        <div class="user-right-container">
            <div class="canlendar-container">
                <Canlendar />
            </div>
        </div>
    </div>
    <el-dialog v-model="gameDialogVisible" class="game-dialog-box">
        {{ gameIds }}
    </el-dialog>
</template>

<script setup lang="ts">
import MyNotify from './UserDetail/MyNotify.vue'
import UserCard from './UserDetail/UserCard.vue'
import Canlendar from './UserDetail/Calendar.vue'
import MyPost from './UserDetail/MyPost.vue'
import MyFans from './UserDetail/MyFans.vue'
import MyFollow from './UserDetail/MyFollow.vue'
import BackWave from './UserDetail/BackWave.vue'
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'

const viewPost = ref(true)
const viewFans = ref(false)
const viewFollow = ref(false)
const gameDialogVisible = ref(false)
const gameIds = ref<number[]>([])

const changeFromCard = (isPost: boolean, isFans: boolean, isFollow: boolean) => {
    viewPost.value = isPost
    viewFans.value = isFans
    viewFollow.value = isFollow
}

onMounted(() => {
    checkUpcomingGame()
})

const checkUpcomingGame = async () => {
    const token = localStorage.getItem('token')
    try {
        const response = await axios.get('/api/user/noticeStartGames', {
            headers: {
                'token': token,
            }
        })
        if (response.data.code == 1) {
            console.log(checkDialogShown())
            console.log(response.data.data)
            if (response.data.data.length > 0) {
                gameDialogVisible.value = true
                setDialogShown()
                gameIds.value = response.data.data.map((item: { gameId: number }) => item.gameId)

            }
        } else {
            ElMessage({
                message: '提醒订阅赛事失败!',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '获取订阅赛事请求发送失败',
            type: 'error',
        })
    }
}

const checkDialogShown = () => {
    return sessionStorage.getItem('gameDialogShown') === 'true'
}

const setDialogShown = () => {
    sessionStorage.setItem('gameDialogShown', 'true');
}
</script>

<style lang="scss">
@import '../../assets/css/frame.scss';

.user-center-container {
    margin-left: 2vw;
    margin-right: 2vw;
}

.game-dialog-box {
    position: absolute;
    display: flex;
    flex-direction: column;
    top: 40%;
    left: 50%;
    transform: translate(-50%, -75%);
    width: 35vw;
}
</style>