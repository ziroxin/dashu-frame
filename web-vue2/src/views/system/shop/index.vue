<template>
  <div class="app-container">
    <!--  操作按钮  -->
    <div style="margin-bottom: 20px;">
      <el-button type="primary" icon="el-icon-plus" @click="shopAdd">新增
      </el-button>
      <el-button type="info" icon="el-icon-edit" @click="shopUpdate">修改
      </el-button>
      <el-button type="danger" icon="el-icon-delete" @click="shopDelete">删除
      </el-button>
    </div>

    <!-- 表格部分 -->
    <el-table :data="shopData" row-key="shopId" style="margin-bottom: 20px;" border
              @selection-change="selectionChangeHandlerOrder"
    >
      <el-table-column type="selection" width="50" header-align="center" align="center" />
      <el-table-column prop="shopName" label="店铺名称" sortable />
      <el-table-column prop="sharingPhoto" label="展示图片" sortable>
        <template slot-scope="scope">
          <el-image class="tableImg" :src="imgUrl+scope.row.sharingPhoto" />
        </template>
      </el-table-column>
      <el-table-column prop="shopPhone" label="联系电话" sortable />
      <el-table-column prop="shopAddress" label="店铺地址" sortable />
      <el-table-column prop="showRecommend" label="是否显示推荐" sortable>
        <template slot-scope="scope">
          <span v-if="scope.row.showRecommend === '0'">显示推荐</span>
          <span v-else-if="scope.row.showRecommend === '1'">不显示推荐</span>
        </template>
      </el-table-column>
      <el-table-column prop="recommendName" label="推荐菜单名称" sortable />
      <el-table-column prop="shopState" label="店铺状态" sortable>
        <template slot-scope="scope">
          <span v-if="scope.row.shopState === '0'">开业</span>
          <span v-else-if="scope.row.shopState === '1'">休息</span>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" sortable />
      <el-table-column prop="updateTime" label="最后修改时间" sortable />
    </el-table>

    <!--分页-->
    <el-pagination style="text-align: center;" background layout="total, pager"
                   :page-size="pager.limit" :current-page="pager.page"
                   :total="totalCount" @current-change="handleCurrentChange"
    />

    <!--  弹窗  -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="shopDataForm" :model="temp" :rules="rules" label-position="right" label-width="150px"
               style="width: 500px; margin-left: 50px;"
      >
        <el-form-item label="店铺名称：" prop="shopName">
          <el-input v-model="temp.shopName" />
        </el-form-item>

        <el-form-item label="展示图片：" prop="sharingPhoto">
          <el-upload
            class="upload-demo"
            action="api/shop/file"
            :on-success="shopFileSuccess"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
            list-type="picture"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>

        <el-form-item label="联系电话：" prop="shopPhone">
          <el-input v-model="temp.shopPhone" />
        </el-form-item>

        <el-form-item label="店铺地址：" prop="shopAddress">
          <el-input v-model="temp.shopAddress" />
        </el-form-item>

        <el-form-item label="是否显示推荐：" prop="showRecommend">
          <el-radio v-model="temp.showRecommend" label="0">显示推荐菜单</el-radio>
          <el-radio v-model="temp.showRecommend" label="1">不显示推荐菜单</el-radio>
        </el-form-item>

        <el-form-item label="推荐菜单名称：" prop="recommendName">
          <el-input v-model="temp.recommendName" />
        </el-form-item>

        <el-form-item label="店铺状态：" prop="shopState">
          <el-radio v-model="temp.shopState" label="0">开业</el-radio>
          <el-radio v-model="temp.shopState" label="1">休息</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitJudgment">保存</el-button>
        <el-button @click="dialogFormVisible=false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {shopAdd, shopDelete, shopList, shopUpdate} from '@/api/shop';

export default {
  name: 'Index',
  data() {
    return {
      fileList: [],
      //  表格数据
      shopData: [],
      //分页
      pager: {page: 1, limit: 10},
      totalCount: 0,
      //每一条数据
      temp: {},
      //选中的数据
      changeData: [],
      //删除的IDS
      shopIds: [],
      textMap: {update: '修改', create: '新增'},
      // 对话框属性
      dialogStatus: '',
      dialogFormVisible: false,
      rules: {
        shopName: [{required: true, message: '请填写店铺名称', trigger: 'blur'}],
        shopAddress: [{required: true, message: '请填写店铺地址', trigger: 'blur'}],
        shopPhone: [{
          required: false, trigger: 'blur', validator: (r, v, b) => {
            (v && !(/^(?:(?:\+|00)86)?1\d{10}$/.test(v))) ? b('手机号格式不正确') : b()
          }
        }]
      },
      imgUrl: process.env.VUE_APP_IMG
    }
  },
  created() {
    this.getShopList()
  },
  methods: {
    // 表格勾选
    selectionChangeHandlerOrder(val) {
      this.changeData = val
    },
    // 查询表格数据
    getShopList() {
      shopList().then(response => {
        this.shopData = response.data
        this.totalCount = response.data.length
      })
    },
    //分页
    handleCurrentChange(page) {
      this.pager.page = page
    },
    resetTemp() {
      this.temp = {
        shopName: '',
        sharingPhoto: '',
        shopPhone: '',
        shopAddress: '',
        showRecommend: '0',
        recommendName: '推荐菜单',
        shopState: '0'
      }
    },
    shopAdd() {
      this.resetTemp()
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      this.$nextTick(() => {
        this.$refs['shopDataForm'].clearValidate()
      })
    },
    shopUpdate() {
      if (this.changeData.length <= 0) {
        this.$message({type: 'warning', message: '请选择一条数据进行修改！'})
      } else if (this.changeData.length > 1) {
        this.$message({type: 'warning', message: '修改时，只允许选择一条数据！'})
      } else {
        this.temp = Object.assign({}, this.changeData[0])
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['shopDataForm'].clearValidate()
        })
      }
    },
    // 提交数据
    submitJudgment() {
      // 表单验证
      this.$refs['shopDataForm'].validate((valid) => {
        if (valid) {
          if (this.dialogStatus === 'update') {
            shopUpdate(this.temp).then(reponse => {
              const {code} = reponse
              if (code === '200') {
                this.$message({type: 'success', message: '修改成功！'})
                this.dialogFormVisible = false
                this.getShopList()
              } else {
                this.$message({type: 'error', message: '修改失败！'})
              }
            })
          } else {
            shopAdd(this.temp).then(reponse => {
              const {code} = reponse
              if (code === '200') {
                this.$message({type: 'success', message: '添加成功！'})
                this.dialogFormVisible = false
                this.getShopList()
              } else {
                this.$message({type: 'error', message: '添加失败！'})
              }
            })
          }
        }
      })
    },
    shopDelete() {
      if (this.changeData.length <= 0) {
        this.$message({message: '请选择一条数据删除！', type: 'warning'})
      } else {
        this.$confirm('确定要删除吗?', '删除提醒', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          // 执行删除
          this.shopIds.push(...this.changeData.map(r => r.shopId))
          shopDelete(this.shopIds).then(response => {
            const {code} = response
            if (code === '200') {
              this.$message({type: 'success', message: '删除成功！'})
              this.getShopList()
            } else {
              this.$message({type: 'error', message: '删除失败！'})
            }
          })
        })
      }
    },
    //删除上传图片后
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    //点击上传图片后
    handlePreview(file) {
      console.log(file);
    },
    //图片上传成功后
    shopFileSuccess(response) {
      this.temp.sharingPhoto = response.data[0].filePath
    }
  }
}
</script>

<style scoped>

</style>
