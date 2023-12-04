<template>
    <el-container class="set-horizonal" style="height: 80vh;margin-top: 5vh;padding-left: 2.5vw;">
        <el-card style="width: 50%;margin-left: 5vw;margin-left: 2.5vw;">
            <el-container>
                <h3>公告标题</h3>
                <el-input style="width: 30vw;margin-left: 2vw;" placeholder="公告标题"/>
            </el-container>
            <wang-editer/>
        </el-card>
        <el-card style="width: 50%;margin-right: 5vw;margin-left: 2.5vw;">

        </el-card>
    </el-container>
</template>

<script>
// import WangEditor from "@/components/AdminComponents/WangEditor.vue"
export default{
    components:{
        WangEditor,
    },
    data(){
        return{

        }
    },
    methods:{

    },
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
</style>