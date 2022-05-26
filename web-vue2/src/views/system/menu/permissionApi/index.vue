<template>
  <div>
    <!--    &lt;!&ndash;      操作按钮  &ndash;&gt;-->
    <!--    <el-button type="primary" icon="el-icon-edit" style="margin-bottom: 20px;">新增</el-button>-->
    <!--    <el-button type="primary" icon="el-icon-edit" style="margin-bottom: 20px;">修改</el-button>-->
    <!--    <el-button type="danger" icon="el-icon-edit" style="margin-bottom: 20px;">删除</el-button>-->

    <!--    &lt;!&ndash;     表格部分 &ndash;&gt;-->
    <!--    <el-table :data="tableData" style="margin-bottom: 20px;" border>-->
    <!--      <el-table-column prop="apiName" label="接口名称" sortable />-->
    <!--      <el-table-column prop="apiRequestUrl" label="接口地址" sortable />-->
    <!--    </el-table>-->

    <!--    &lt;!&ndash;     分页组件 &ndash;&gt;-->
    <!--    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />-->
  </div>
</template>

<script>
export default {
  name: 'PermissionApi'
}
</script>

<style scoped>

</style>
