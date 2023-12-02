<template>
    <el-container style="height: 75vh;margin-top: 5vh;" class="set-horizonal">
        <el-container style="margin-left: 5vw;width: 55vw;" class="set-vertical">
            <el-switch
                v-model="viewUserBanned"
                style="--el-switch-on-color: #ff4949; --el-switch-off-color: #13ce66"
                active-text="被封禁用户列表"
                inactive-text="正常用户列表"
            />
            <el-card v-if="viewUserBanned==true" style="height: 70vh;">
                <el-input
                    v-model="input2"
                    placeholder="Please Input"
                    :suffix-icon="Search"
                />
                被封禁用户列表
            </el-card>
            <el-card v-if="viewUserBanned == false" style="height: 70vh;">
                <el-input
                    v-model="input2"
                    placeholder="Please Input"
                    :suffix-icon="Search"
                />
                正常用户列表
            </el-card>
        </el-container>
        <el-container class="set-vertical" style="margin-right: 5vw;width: 30vw;margin-left: 5vw;margin-top: 5vh;">
            <el-card style="height: 35vh;">
                新增用户曲线
            </el-card>
            <el-card style="margin-top: 2vh;height: 35vh;">
                可疑用户
            </el-card>
        </el-container>
    </el-container>
</template>

<script>
export default{
    data(){
        return{
            viewUserBanned:false,
        }
    },
    methods:{

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
</style>