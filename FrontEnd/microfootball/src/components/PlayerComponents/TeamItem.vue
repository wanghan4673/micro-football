<template>
    <el-card  shadow="hover" style="width: 400px; height: 220px; border-radius: 15px;background-color: #edfdd029 ">
        <div id="teamcard">
            <img style="height: 100%; padding: 10px; opacity: 0.8; border-radius: 15px;" :src="team.logo" />
            <div>
                <p class="team-name" style="font-size: larger; font-weight: bold;">{{ team.name }}</p>
                <p>国家 {{ team.country }}</p>
                <p>Since {{ team.founded }}</p>
            </div>
        </div>
    </el-card>
</template>     

<script setup>
import { onMounted, ref, nextTick, watch, defineProps } from 'vue';
// props 接收 dataList
const props = defineProps(['team']);

</script>

<style lang="scss" scoped>
#teamcard {
    width: 100%;  // 占满父容器宽度
    min-width: 250px;  // 设置最小宽度
    display: flex;
    // flex-direction: column;  // 垂直排列
    justify-content: center;
    align-items: center;
    text-align: start;  // 文字居中

    p {
        word-wrap: break-word;  // 允许文本换行
        // overflow: hidden;  // 防止内容溢出
    }

    img {
        max-width: 100%;  // 图片最大宽度为100%，防止溢出
        height: auto;  // 图片高度自适应
        margin-left: -120px;
    }
    .team-name {
        max-width: 100px;  // 根据需要调整宽度
        // overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
}

// 使用媒体查询来处理不同屏幕尺寸
@media (max-width: 600px) {
    #playercard {
        min-width: 100%;  // 小屏幕下卡片宽度占满屏幕
    }
}

</style>