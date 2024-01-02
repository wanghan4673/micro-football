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
</template>

<script setup lang="ts">
import MyNotify from './UserDetail/MyNotify.vue'
import UserCard from './UserDetail/UserCard.vue'
import Canlendar from './UserDetail/Calendar.vue'
import MyPost from './UserDetail/MyPost.vue'
import MyFans from './UserDetail/MyFans.vue'
import MyFollow from './UserDetail/MyFollow.vue'
import BackWave from './UserDetail/BackWave.vue'
import { ref } from 'vue'

const viewPost = ref(true)
const viewFans = ref(false)
const viewFollow = ref(false)

const changeFromCard = (isPost: boolean, isFans: boolean, isFollow: boolean) => {
    viewPost.value = isPost
    viewFans.value = isFans
    viewFollow.value = isFollow
}
</script>

<style lang="scss">
@import '../../assets/css/frame.scss';

.user-center-container {
    margin-left: 2vw;
    margin-right: 2vw;
}
</style>