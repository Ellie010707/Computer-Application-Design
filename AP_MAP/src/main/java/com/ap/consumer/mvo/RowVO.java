package com.ap.consumer.mvo;

import lombok.Data;

@Data
public class RowVO {

	public String SIGUN_CD;	//시군코드
	public String SIGUN_NM;	//시군명
	public String BIZPLC_NM;	//사업장명
	public String LICENSG_DE;	//인허가일자
	public String LICENSG_CANCL_DE;	//인허가취소일자
	public String BSN_STATE_DIV_CD;	//영업상태구분코드
	public String BSN_STATE_NM;	//영업상태명
	public String CLSBIZ_DE;	//폐업일자
	public String LOCPLC_FACLT_TELNO;	//소재지시설전화번호
	public String LOCPLC_AR_INFO;	//소재지면적정보
	public String ROADNM_ZIP_CD;	//도로명우편번호
	public String REFINE_ROADNM_ADDR;	//소재지도로명주소
	public String REFINE_LOTNO_ADDR;	//소재지지번주소
	public String REFINE_ZIP_CD;	//소재지우편번호
	public String REFINE_WGS84_LAT;	//WGS84위도
	public String REFINE_WGS84_LOGT;	//WGS84경도
	public String BIZCOND_DIV_NM_INFO;	//업태구분명정보
	public String X_CRDNT_VL;	//X좌표값
	public String Y_CRDNT_VL;	//Y좌표값
	public String STOCKRS_DUTY_DIV_NM;	//축산업무구분명
	public String SFRMPROD_PROCSBIZ_DIV_NM;	//축산물가공업구분명
	public String STOCKRS_IDNTFY_NO;	//축산고유번호
	public String RIGHT_MAINBD_IDNTFY_NO;	//권리주체일련번호
	public String TOT_EMPLY_CNT;	//총종업원수


}
