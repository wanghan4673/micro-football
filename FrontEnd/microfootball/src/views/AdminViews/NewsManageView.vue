<template>
    <el-container style="height: 80vh;margin-top: 5vh;" class="set-horizonal">
        <el-container style="margin-left: 5vw;width: 55vw;" class="set-vertical">
            <el-tabs tab-position="left">
                <el-tab-pane label="英超">
                    <el-card class="news-list-card" shadow="always">
                        <h3>当前英超新闻列表</h3>
                    </el-card>
                </el-tab-pane>
                <el-tab-pane label="西甲">
                    <el-card class="news-list-card" shadow="always">
                        <h3>当前西甲新闻列表</h3>
                    </el-card>
                </el-tab-pane>
                <el-tab-pane label="德甲">
                    <el-card class="news-list-card" shadow="always">
                        <h3>当前德甲新闻列表</h3>
                    </el-card>
                </el-tab-pane>
                <el-tab-pane label="...">
                    <el-card class="news-list-card" shadow="always">
                        <h3>当前某某新闻列表</h3>
                    </el-card>
                </el-tab-pane>
            </el-tabs>
        </el-container>
        <el-container class="set-vertical" style="margin-right: 5vw;width: 30vw;margin-left: 5vw;">
            <el-card class="news-list-card" shadow="always">
                <el-container>
                    <h3>用户来稿</h3>
                    <el-button type="primary" size="small" style="margin-left: 2vw;">添加新闻</el-button>
                </el-container>
                <el-scrollbar style="height: 75vh;">
                    <el-card style="height: 15vh;margin-top: 2vh;">
                        <h3>关于足协占用刷段操场的投诉</h3>
                        <h4 style="margin-top: 1vh;">近日，足协多次占用....</h4>
                        <h5 style="margin-top: 1vh;">2023-12-03 16:41  来信人：cinderlord</h5>
                    </el-card>
                </el-scrollbar>
            </el-card>
        </el-container>
    </el-container>
</template>

<script>
export default {
    components: {
        
    },
    data() {
        return {
            
        }
    },
    methods: {

    }
}
</script>

<style scoped>
.set-vertical{
    display: flex;
    flex-direction: column;
}
.set-horizonal{
    display: flex;
    flex-direction: row;
}
.news-list-card{
    height: 80vh;
}
</style>