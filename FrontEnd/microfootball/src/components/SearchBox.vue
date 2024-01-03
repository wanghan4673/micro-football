<template>
  <a-space direction="vertical">
    <a-input-search
      v-model:value="searchText"
      :placeholder="defaultText"
      enter-button="Search"
      size="large"
      @search="onSearch"
      :style="{ width: '600px', height: '40px' }"
    >
    </a-input-search>
  </a-space>
</template>

<script>

  import { ref } from 'vue'
  export default{
    components:{
    },
    props:['defaultText'],
    setup(props,{emit}){
      
      const defaultText = ref(props.defaultText)
      const searchText = ref('')
      const onSearch = searchValue => {
        console.log('搜索框数据为', searchValue);
        if(searchValue == ''){
          emit("error-event", '请在搜索框中输入内容')
          console.log('请在搜索框中输入内容')
        }
        else{
          emit("search-event", searchValue)
        }
      };

      return{
        onSearch,
        searchText,
        defaultText
      }
    },
  }
</script>

<style scoped>

</style>