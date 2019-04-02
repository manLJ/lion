<template >
  <div style="padding:30px;">
    <el-alert :closable="false" title="menu 1">
      <router-view />
    </el-alert>

    <el-form :model="numberValidateForm" ref="numberValidateForm" label-width="100px" class="demo-ruleForm">
      <el-form-item
        label="年龄"
        prop="age"
        :rules="rules"
      >
        <el-input type="age" v-model.number="numberValidateForm.age" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm()">提交</el-button>
        <el-button @click="resetForm()">重置</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>
<script>
  export default {
    data() {
      return {
        numberValidateForm: {
          age: ''
        },
        rules:[
          { required: true, message: '年龄不能为空'},
          { type: 'number', message: '年龄必须为数字值'}
        ]
      };
    },
    methods: {
      submitForm() {
        this.$refs.numberValidateForm.validate((valid,fields) => {
          if (valid) {
            console.log(valid)
            alert('submit!');
          } else {
            console.log('error submit!!',valid);
            console.log(fields)
            return false;
          }
        });
      },
      resetForm() {
        this.$refs.numberValidateForm.resetFields();
      }
    }
  }
</script>
