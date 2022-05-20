<template>
  <div>
    <!--    操作按钮  -->
    <el-button type="primary" icon="el-icon-edit" style="margin-bottom: 20px;">新增</el-button>
    <el-button type="primary" icon="el-icon-edit" style="margin-bottom: 20px;">修改</el-button>
    <el-button type="danger" icon="el-icon-edit" style="margin-bottom: 20px;">删除</el-button>

    <!--   表格部分 -->
    <el-table :data="tableData" style="margin-bottom: 20px;" border @selection-change="selectionChangeHandlerOrder">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="permissionName" label="按键名称" sortable />
      <el-table-column label="操作">
        <template>
          <PermissionApi />
        </template>
      </el-table-column>
    </el-table>

    <!--   分页组件 -->
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" />
  </div>
</template>

<script>
// 引入分页组件
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
// 引入接口组件
import PermissionApi from '../permissionApi/index'
export default {
  name: 'PermissionButton',
  components: {Pagination, PermissionApi},
  // props: ['ButtonTableData'],
  data() {
    return {
      tableData: [],
      // 查询数据条数
      total: 0,
      // 分页属性
      listQuery: {page: 1, limit: 10},
      // 勾选的数据
      changeData: []
    }
  },
  // created() {
  //
  // },
  methods: {
    // 表格勾选
    selectionChangeHandlerOrder(val) {
      this.changeData = val
    }

  }
}
</script>

<style scoped>

</style>
