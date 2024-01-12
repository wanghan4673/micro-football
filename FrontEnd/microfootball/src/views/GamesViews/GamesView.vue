<template>
    <div style="padding-bottom:50px">
        <!-- <TitleElement :mainTitle="titleText" :subTitle="subTitleText" style="padding:25px" /> -->
        <div style="display: flex;">
            <PlayerSideNave />
            <div style="display: flex;flex-direction:column;width: 100%; padding: 20px;">
                <div style="margin: 20px;display: flex;gap: 10px;">
                    <a-space direction="vertical" :size="20">
                        <a-date-picker v-model:value="dateRange" />
                    </a-space>
                    <a-space direction="vertical">
                        <a-space warp>
                            <a-button type="primary" @click="search">搜索</a-button>
                        </a-space>
                    </a-space>
                </div>
                <div id="gamecard">
                    <GameItem style="" v-for="game in gameList" :key="game" :game="game" @click="toGameInfo(game)" />
                </div>
            </div>
        </div>

        <div style="display: flex;justify-content: center; margin: 20px;">
            <el-pagination @current-change="handleCurrentChange" background layout="prev, pager, next" :total=totalElements
                :page-size="pageSize" :current-page=page />
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted,watch } from 'vue'
import TitleElement from '@/components/TitleElement.vue'
import SearchBox from '@/components/SearchBox.vue'
import ImageButton from '@/components/ImageButton.vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import GameItem from '@/components/PlayerComponents/GameItem.vue'
import PlayerSideNave from '@/components/PlayerComponents/PlayerSideNav.vue'
import { useGeneralStore } from '../../stores/general.ts'
import { useRouter } from 'vue-router'

const router = useRouter()
const store = useGeneralStore()
const searchLeagueSet = ref([])

const dateRange = ref();
const BaseUrl = '/api/game'
const titleText = ref('联赛赛事信息')
const subTitleText = ref('不容错过的联赛赛事一览')
const searchDefaultText = ref('搜索联赛信息')
const gameList = ref([])
const isSearch = ref(false)
let totalElements = ref(0)
let page = ref(1)
const pageSize = 20
const getgame = async () => {
    gameList.value = []
    try {
        const response = await axios.get(`${BaseUrl}?page=${page.value}&size=${pageSize}&league=${store.leagueChoice}`)
        console.log(response)
        gameList.value.push(...response.data.data.gameSimpleInfos);
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
    getgame()
}

watch(() => store.leagueChoice, (newleague) => {
    getgame()
})

onMounted(() => {
    store.leagueChoice = '全部赛事'
    getgame()
})


const search = async () => {
    gameList.value = []
    isSearch.value = false
    searchLeagueSet.value = []
    if (store.leagueChoice === '全部赛事') {
        searchLeagueSet.value.push('英超')
        searchLeagueSet.value.push('西甲')
        searchLeagueSet.value.push('意甲')
        searchLeagueSet.value.push('德甲')
        searchLeagueSet.value.push('法甲')
        searchLeagueSet.value.push('中超')
    }else{
        searchLeagueSet.value.push(store.leagueChoice)
    }
    if (searchLeagueSet.value.size == 0) {
        ElMessage({
            message: "请选择联赛",
            type: "error"
        });
        return
    }
    if (dateRange.value === undefined) {
        ElMessage({
            message: "请选择日期",
            type: "error"
        });
        return
    }
    let yearStr = `${dateRange.value.$y.toString()}`
    let monthStr = ''
    let dayStr = ''

    let month = dateRange.value.$M + 1
    if (month < 10) {
        monthStr = `0${month.toString()}`
    }
    else {
        monthStr = `${month.toString()}`
    }
    if (dateRange.value.$D < 10) {
        dayStr = `0${dateRange.value.$D}`
    }
    else {
        dayStr = `${dateRange.value.$D}`
    }
    let dateStr = `${yearStr}-${monthStr}-${dayStr}`
    console.log(dateStr)
    for (const league of searchLeagueSet.value) {
        try {
            console.log(league)
            const response = await axios.get(`${BaseUrl}/info?date=${dateStr}&leagueName=${league}`)//,formData)
            console.log(response);
            gameList.value.push(...response.data.data);
            console.log('gameList', gameList.value);
            isSearch.value = true
        } catch (err) {
            ElMessage({
                message: '获取失败',
                type: 'error'
            });
            console.error(err);
        }
    }
}

const toGameInfo = (game) => {
    router.push({
        path: `/Games/${game.id}`
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

.button-container {
    display: flex;
    gap: 0px;
    /* Optional: Adjust the gap between buttons */
    justify-content: center;
    align-items: center;
    padding: 25px;
}

.text-text {
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: bold;
    color: black;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
    padding: 25px;
    margin-top: 25px;
}

.list-content {
    min-height: 500px;
}

#gamecard {
    width: 100%;

    display: flex;
    justify-content: flex-start;
    /* 对齐到开始位置 */
    flex-wrap: wrap;
    /* 允许内容换行 */
    gap: 10px;
    /* 项目之间的间距 */
}

.game-item {
    flex: 1 0 22%;
    /* 每个项目占用大约四分之一的宽度 */
    max-width: 22%;
    /* 确保宽度不超过四分之一 */
}
</style>