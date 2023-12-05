<template>
    <div class="grid-three-container">
        <BackWave />
        <div class="user-left-container">
            <div class="receive-container">
                <MyReceive />
            </div>
        </div>
        <div class="user-center-container">
            <div class="usercard-container">
                <UserCard />
            </div>
            <div class="change-container">
                <MyPost />
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
import MyReceive from './UserDetail/MyReceive.vue'
import UserCard from './UserDetail/UserCard.vue'
import Canlendar from './UserDetail/Calendar.vue'
import MyPost from './UserDetail/MyPost.vue'
import MyFans from './UserDetail/MyFans.vue'
import MyFollow from './UserDetail/MyFollow.vue'
import EditProfile from './UserDetail/EditProfile.vue'
import BackWave from './UserDetail/BackWave.vue'

</script>

<style>
@import '../../assets/css/frame.scss';
</style>