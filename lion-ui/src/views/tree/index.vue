<template>
  <div class="app-container">


    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;"/>
    <el-tree
      ref="tree2"
      :data="data2"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />
    <el-button @click="showResult">获取结果</el-button>
    <vue-tinymce v-model="editorContent" @contentChange="setCaseContentText"></vue-tinymce>
  </div>
</template>

<script>
  import tinymceEditor from '@/components/Tinymce'

  export default {
    components: {
      "vue-tinymce":tinymceEditor
    },
    data() {
      return {
        editorContent:"111111111111",
        editorContentText:"",
        filterText: '',
        data2: [{
          id: 1,
          label: 'Level one 1',
          children: [{
            id: 4,
            label: 'Level two 1-1',
            children: [{
              id: 9,
              label: 'Level three 1-1-1'
            }, {
              id: 10,
              label: 'Level three 1-1-2'
            }]
          }]
        }, {
          id: 2,
          label: 'Level one 2',
          children: [{
            id: 5,
            label: 'Level two 2-1'
          }, {
            id: 6,
            label: 'Level two 2-2'
          }]
        }, {
          id: 3,
          label: 'Level one 3',
          children: [{
            id: 7,
            label: 'Level two 3-1'
          }, {
            id: 8,
            label: 'Level two 3-2'
          }]
        }],
        defaultProps: {
          children: 'children',
          label: 'label'
        }
      }
    },
    watch: {
      filterText(val) {
        this.$refs.tree2.filter(val)
      }
    },

    methods: {
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      setCaseContentText(contentText){
        this.editorContentText = contentText;
      },
      showResult(){
        console.log(this.editorContentText);
        console.log(this.editorContent)
      }
    }
  }
</script>

