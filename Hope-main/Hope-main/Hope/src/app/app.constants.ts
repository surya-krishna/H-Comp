//export const mainUrl="http://localhost:8090";

export const constants={
    mainUrl:"http://localhost:8090",
    noSideNavRoutes:['/login','/','/userReg','/docReg'],
    headerFields:['PatientName','PatientGender','PatientAge','ReportDate','LabName','LabAddress','LabPhone'],
    testTablefields:['ReportDetails_TestName','ReportDetails_TestValue','ReportDetails_TestUnits','ReportDetails_NormalMin','ReportDetails_NormalMax'],
    headerMap:{
               PatientName:'nameOnReport',
               PatientGender:'gender',
               PatientAge:'age',
               ReportDate:'reportDate',
               LabName:'labName',
               LabAddress:'labAddress',
               LabPhone:'labPhone'},
    testtableMap:{
        ReportDetails_TestName:'testName',
        ReportDetails_TestValue:'testValue',
        ReportDetails_TestUnits:'units',
        ReportDetails_NormalMin:'testMinNormal',
        ReportDetails_NormalMax:'testMaxNormal'
    }
}



