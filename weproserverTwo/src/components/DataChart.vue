<template>
  <section class="container">
    <div class="row">
       <CylinderChart class="col-lg-7"></CylinderChart>
       <WordChart class="col-lg-4"></WordChart>
      <TableChart class="col-lg-12"></TableChart>
    </div>
  </section>
</template>

<script>
  import CylinderChart from "../components/dataChart/CylinderChart"
  import WordChart from "../components/dataChart/WordChart"
  import TableChart from "../components/dataChart/TableChart"
    export default {
        name: "DataChart",
      components:{
        CylinderChart,WordChart,TableChart
      },
    }
</script>

<style scoped>

</style>
