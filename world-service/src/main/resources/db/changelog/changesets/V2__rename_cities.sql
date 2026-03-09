alter table cities
    rename to locations;

alter table locations
    rename column year_city_status to year_status;

alter index idx_cities_region_id rename to idx_locations_region_id;
alter index idx_cities_timezone_id rename to idx_locations_timezone_id;
alter index idx_cities_label rename to idx_locations_label;
alter index idx_cities_name rename to idx_locations_name;

alter table locations
    rename constraint cities_pkey to locations_pkey;

alter table locations
    rename constraint cities_region_id_fkey to locations_region_id_fkey;
alter table locations
    rename constraint cities_timezone_id_fkey to locations_timezone_id_fkey;