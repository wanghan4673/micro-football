<template>
    <el-card shadow="hover" style="width: 600px; border-radius: 15px;background-color: #ffab7729 ">
        <div id="playercard">
            <div style="width: 100%; display: flex;flex-direction: column;">
                <p style=" font-size: x-large; font-weight: bold; color: #043c8aa6;">{{ year }}-{{ month }}-{{ day }}&nbsp&nbsp{{ hour }}:{{ minute }}:{{ second }}</p>
                <div style="display: flex;justify-content: space-between;padding: 30px;">
                    <div style="width: 30%;">
                        <div><img :src="game.homeTeamLogo"/></div>
                        <div class="teamText">{{ game.homeTeamName }}</div>
                    </div>
                    <div style="width: 40%; display: flex;justify-content: center; align-items: center; font-size: xxx-large; font-weight: bolder;">
                        <p>{{ game.homeGoal }} : {{ game.awayGoal }}</p>
                    </div>
                    <div style="width: 30%;">
                        <div><img :src="game.awayTeamLogo"/></div>
                        <div class="teamText">{{ game.awayTeamName }}</div>
                    </div>
                </div>
            </div>
        </div>
    </el-card>
</template>     

<script setup>
import { onMounted, ref, nextTick, watch, defineProps } from 'vue';
// props 接收 dataList
const props = defineProps(['game']);
let year = ref('')
let month = ref('')
let day = ref('')
let hour = ref('')
let minute = ref('')
let second = ref('')


onMounted(()=>{
    console.log(props.game);
    initdata(props.game);
    convertToBeijingTime(props.game.date)
})

function initdata(game){
    if(!hasTimePassed(game.date)){
        game.homeGoal = '-'
        game.awayGoal = '-'
    }
}

function hasTimePassed(utcDateString) {
    const utcDate = new Date(utcDateString);
    const now = new Date();
    return now > utcDate;
}

function convertToBeijingTime(isoString) {
    const utcDate = new Date(isoString);
    const beijingTimeOffset = 8 * 60; // 北京时间比UTC时间快8小时
    utcDate.setMinutes(utcDate.getMinutes() + beijingTimeOffset);

    year.value = utcDate.getFullYear().toString();
    month.value = (utcDate.getMonth() + 1).toString().padStart(2, '0'); // getMonth() 返回的月份是从0开始的
    day.value = utcDate.getDate().toString().padStart(2, '0');
    hour.value = utcDate.getHours().toString().padStart(2, '0');
    minute.value = utcDate.getMinutes().toString().padStart(2, '0');
    second.value = utcDate.getSeconds().toString().padStart(2, '0');
}



</script>

<style lang="scss" scoped>
#playercard {
    width: 100%;  // 占满父容器宽度
    min-width: 250px;  // 设置最小宽度
    display: flex;
    // flex-direction: column;  // 垂直排列
    justify-content: center;
    align-items: center;
    text-align: center;  // 文字居中

    p {
        word-wrap: break-word;  // 允许文本换行
        overflow: hidden;  // 防止内容溢出
    }

    img {
        max-width: 100%;  // 图片最大宽度为100%，防止溢出
        height: auto;  // 图片高度自适应
    }
    .player-name {
        max-width: 150px;  // 根据需要调整宽度
        // overflow: hidden;
        white-space: nowrap;
        text-overflow:ellipsis;
    }
}

// 使用媒体查询来处理不同屏幕尺寸
@media (max-width: 600px) {
    #playercard {
        min-width: 100%;  // 小屏幕下卡片宽度占满屏幕
    }
}

.teamText{
    font-size: x-large;
    font-weight: bolder;
}

</style>