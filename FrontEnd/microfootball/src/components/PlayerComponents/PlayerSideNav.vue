<template>
    <div style="width: 200px;">
        <!-- <div class="select-tips">
            <span class="tips-text">选择联赛</span>
        </div> -->
        <el-menu class="tags-container" default-active="1">
            <el-menu-item v-for="(league, index) in leagues_logo" :key="index" :index="`${index + 1}`" @click="SelectItem(index)">
                <img :src="league.logo" class="logo-show">
                <span class="tag-text">{{ league.name }}</span>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { useGeneralStore} from '../../stores/general.ts'
import { useRouter } from 'vue-router';
const router = useRouter()
const store = useGeneralStore()
const leagues_logo = [
    { "logo": require('@/assets/img/football_logo.png'), "name": "全部赛事" },
    { "logo": require('@/assets/img/pmlogo.png'), "name": "英超" },
    { "logo": require('@/assets/img/lllogo.png'), "name": "西甲" },
    { "logo": require('@/assets/img/salogo.png'), "name": "意甲" },
    { "logo": require('@/assets/img/bllogo.png'), "name": "德甲" },
    { "logo": require('@/assets/img/le1logo.png'), "name": "法甲" },
    { "logo": require('@/assets/img/cslogo.png'), "name": "中超" },]
const SelectItem = (index) =>{
    store.leagueChoice = leagues_logo[index].name;
}
</script>

<style scoped>
.logo-show {
    /* 展示tag的logo */
    width: 30px;
    height: 30px;
    border-radius: 50%;
    margin-left: 40px;
    margin-right: 10px;
}

.tips-text {
    padding: 15px;
    font-size: 18px;
    color: cornflowerblue;
}

.select-tips {
    /* 提示选择联赛 */
    margin-top: 10px;
    margin-bottom: 10px;
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.tags-container {
    /* 左侧选择联赛菜单 */
    margin-top: 15px;
    /* background-color: #F5F7FA; */
}

.tag-text {
    
    font-size: 20px;
    /* font-weight: bold; */
}
.post-button-container {
    margin-top: 10px;
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}
</style>
