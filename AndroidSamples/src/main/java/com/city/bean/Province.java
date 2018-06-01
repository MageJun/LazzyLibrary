package com.city.bean;

import java.util.List;


/**
 * 省级数据对象
 */
public class Province {

    /**
     * name : 北京市
     * cityList : [{"name":"北京市","cityList":[{"name":"东城区","id":"110101"},{"name":"西城区","id":"110102"},{"name":"朝阳区","id":"110105"},{"name":"丰台区","id":"110106"},{"name":"石景山区","id":"110107"},{"name":"海淀区","id":"110108"},{"name":"门头沟区","id":"110109"},{"name":"房山区","id":"110111"},{"name":"通州区","id":"110112"},{"name":"顺义区","id":"110113"},{"name":"昌平区","id":"110114"},{"name":"大兴区","id":"110115"},{"name":"怀柔区","id":"110116"},{"name":"平谷区","id":"110117"},{"name":"密云区","id":"110118"},{"name":"延庆区","id":"110119"}],"id":"110100"}]
     * id : 110000
     */

    private String name; //省名
    private String id; //省ID
    private List<City> cityList;//省内内地级市集合

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    @Override
    public String toString() {
        return "Province{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", cityList=" + cityList +
                '}';
    }

    public static class City {
        /**
         * name : 北京市
         * cityList : [{"name":"东城区","id":"110101"},{"name":"西城区","id":"110102"},{"name":"朝阳区","id":"110105"},{"name":"丰台区","id":"110106"},{"name":"石景山区","id":"110107"},{"name":"海淀区","id":"110108"},{"name":"门头沟区","id":"110109"},{"name":"房山区","id":"110111"},{"name":"通州区","id":"110112"},{"name":"顺义区","id":"110113"},{"name":"昌平区","id":"110114"},{"name":"大兴区","id":"110115"},{"name":"怀柔区","id":"110116"},{"name":"平谷区","id":"110117"},{"name":"密云区","id":"110118"},{"name":"延庆区","id":"110119"}]
         * id : 110100
         */

        private String name;//地级市名字
        private String id;//地级市ID
        private List<Area> cityList;//区县城市集合

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Area> getCityList() {
            return cityList;
        }

        public void setCityList(List<Area> cityList) {
            this.cityList = cityList;
        }

        @Override
        public String toString() {
            return "City{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    ", cityList=" + cityList +
                    '}';
        }

        public static class Area {
            /**
             * name : 东城区
             * id : 110101
             */

            private String name;//区县名字
            private String id;//区县ID

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            @Override
            public String toString() {
                return "Area{" +
                        "name='" + name + '\'' +
                        ", id='" + id + '\'' +
                        '}';
            }
        }
    }
}
