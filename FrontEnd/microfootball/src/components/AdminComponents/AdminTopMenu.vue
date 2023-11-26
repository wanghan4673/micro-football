<template>
    <div class="topMenu">
        <el-icon size="1.2em" style="font-weight: bold;color: #fff;margin-right: 1vw;"><Collection /></el-icon>
        <p style="font-weight: bold;color: #fff;font-size: 1.2em;cursor: pointer;" @click="this.$router.push(`/`);">题库系统</p>
        <el-menu :default-active="active" mode="horizontal" active-text-color="#ffd04b" 
            @select="handleMenuSelect" style="border: 0 !important;margin-left: 20vw;width: 40vw;">
            <el-menu-item index="viewrepo">系统总览</el-menu-item>
            <el-menu-item index="viewrepo">用户管理</el-menu-item>
            <el-menu-item index="managetest">论坛管理</el-menu-item>
            <el-menu-item index="managestu">新闻管理</el-menu-item>
            <el-menu-item index="managestu">公告管理</el-menu-item>
        </el-menu>
        <el-container style="margin-left: 20vw;cursor: pointer;color: #fff;">| 登录 / 注册</el-container>
    </div>
</template>

<script setup>
import { ref } from 'vue'

const activeIndex = ref('1')
const handleSelect = (key, keyPath) => {
    console.log(key, keyPath)
}
</script>

<style scoped>
.topMenu{
    width: 100%;
    height: 7vh;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    border-bottom: 1px solid;
    padding-bottom: 1vh;
}
.website-title {
    font-weight: bold;
    font-size: 1.2rem;
    display: flex;
    align-items: center;
}
.font-set{
    color: #000000;
}
</style>
