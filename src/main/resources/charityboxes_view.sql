select box.charity_box_id,box.charity_box_barcode,box.status,box.charity_box_number,box.charity_box_name,
box.sub_location_id,sub.sub_location_name,loc.location_id,loc.location_name,reg.TM_REGION_ID as REGION_ID,reg.region_name 
,reg.TM_CITY_ID as EMARAH_ID,emarah.ARABIC_VALUE as EMARAH_NAME
from TM_CHARITY_BOXES box 
left join TM_SUB_LOCATIONS sub on box.sub_location_id = sub.SUB_LOCATION_ID
left join TM_LOCATIONS loc on sub.LOCATION_ID = loc.LOCATION_ID
left join TM_REGION reg on loc.REGION_ID = reg.TM_REGION_ID
left join FND_LOOKUP_VALUES emarah on  emarah.LOOKUP_CODE = TO_CHAR(reg.TM_CITY_ID) and (LOOKUP_TYPE)='CITIES'