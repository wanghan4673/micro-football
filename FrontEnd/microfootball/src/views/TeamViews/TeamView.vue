<template>
    <div style="padding-bottom:50px;display: flex; flex-direction: column;justify-content: center;align-items: center;">
        <TitleElement :mainTitle="titleText" :subTitle="subTitleText" />
        <div class="search-element">
            <SearchBox :defaultText="searchDefaultText" @search-event="search" @error-event="errorEvent" />
        </div>
        <div id="teamcard" style="display: flex;justify-content: center;align-items: center;">
            <TeamItem v-for="team in teamList" :key="team" :team="team" @click="toteaminfo(team)" />
        </div>
        <div style="display: flex;justify-content: center; margin: 20px;">
            <el-pagination @current-change="handleCurrentChange" background layout="prev, pager, next" :total=totalElements
                :page-size="pageSize" :current-page=page />
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';
import TitleElement from '@/components/TitleElement.vue';
import SearchBox from '@/components/SearchBox.vue';
import TeamListComponent from '@/components/TeamListComponent.vue';
import TeamItem from '@/components/PlayerComponents/TeamItem.vue';
import {useRouter} from 'vue-router';

const BaseUrl = '/api/team';
const titleText = ref('联赛球队信息');
const subTitleText = ref('背后英雄，揭秘每支球队的独特魅力');
const searchDefaultText = ref('搜索球队信息');
const teamList = ref([]);
const isSearch = ref(false);
const router = useRouter()

let totalElements = ref(0)
let page = ref(1)
const pageSize = 20
const getteam = async () => {
    teamList.value = []
    try {
        const response = await axios.get(`${BaseUrl}?page=${page.value}&size=${pageSize}`)
        console.log(response)
        teamList.value.push(...response.data.data.teamSimpleInfos);
        totalElements.value = response.data.data.count;
    } catch (err) {
        ElMessage({
            message: '获取失败',
            type: 'error'
        });
        console.error(err);
    }
}

const handleCurrentChange = async (newPage) => {
    page.value = newPage
    getteam()
}

onMounted(() => {
    getteam()
})

const search = async text => {
    teamList.value = [];
    isSearch.value = false;
    try {
        const response = await axios.get(`${BaseUrl}/info?searchKey=${text}`);
        console.log(response);
        teamList.value.push(...response.data.data);
        isSearch.value = true;
    } catch (err) {
        ElMessage({
            message: '获取失败',
            type: 'error'
        });
        console.error(err);
    }
};

const errorEvent = text => {
    ElMessage({
        message: '搜索框为空',
        type: 'error'
    });
};

const toteaminfo = (team) => {
    router.push({
        path: `/team/${team.id}`
    })
}

</script>

<style lang="scss" scoped>
.search-element {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 50px;
}

.list-content {
    min-height: 500px;
}

#teamcard {
    display: flex;
    justify-content: flex-start;
    /* 对齐到开始位置 */
    flex-wrap: wrap;
    /* 允许内容换行 */
    gap: 10px;
    /* 项目之间的间距 */
}


</style>