<template>
    <div style="display: flex; justify-content: space-between; flex-wrap: wrap; width: 90%;">
        <div style="display: flex;">
            <el-input style=" max-width: 300px;" v-model="forumstore.keyword" placeholder="关键词" @change="keywordchange">
                <template #suffix>
                    <el-icon class="el-input__icon">
                        <Search />
                    </el-icon>
                </template>
            </el-input>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { useForumStore } from '../../stores/forum.ts'
let forumstore = useForumStore()

const keywordchange = () =>{
    forumstore.getPosts = !forumstore.getPosts
    console.log(forumstore.getPosts)
}
</script>

<style lang="scss" scoped>
</style>