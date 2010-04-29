package com.wannalunch.taglib

class PartnerTagLib {

  static namespace = "p"
  
  static logos = [africankitchen: "part_1_africankitchen.jpg",
                  bonaparte: "part_2_bonaparte.jpg",
                  bossanova: "part_3_bossanova.jpg",
                  cafetao: "part_4_cafetao.jpg",
                  cafevs: "part_5_cafevs.jpg",
                  cestlavie: "part_6_cestlavie.jpg",
                  chakra: "part_7_chakra.jpg",
                  clazz: "part_8_clazz.jpg",
                  cubanita: "part_9_cuba.jpg",
                  fahle: "part_10_fahle.jpg",
                  island: "part_11_island.jpg",
                  silk: "part_12_silk.jpg",
                  sushicafe: "part_13_sushicafe.jpg",
                  sushicat: "part_14_sushicat.jpg",
                  umbra: "part_15_umbra.jpg"
                  ]
  
  def logo = { attrs ->
    def location = attrs.lunch.location.replaceAll("[^a-zA-Z]", "").toLowerCase()
    def logo = 'part_unknown.jpg'
    logos.each {
      if (location.contains(it.key)) {
        logo = it.value
        return
      }
    }
    
    out << g.resource(dir: 'img', file: logo)    
  }
}
