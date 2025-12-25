package com.NCOpossum.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;



public class AISearch {
    public AxisAlignedBB searchArea;
    private Entity entity;

    public AISearch(Entity opossum) {
        // Ensure that opossum is properly passed and assigned before trying to access posX, posY, and posZ
        if (opossum != null) {
            entity = opossum;
            double horizontalRadius = 10.0;
            double verticalRadius = 5.0;

            // Create an AABB for searching nearby items within the defined radius
            searchArea = new AxisAlignedBB(entity.posX - horizontalRadius, entity.posY - verticalRadius,
                entity.posZ - horizontalRadius, entity.posX + horizontalRadius, entity.posY + verticalRadius,
                entity.posZ + horizontalRadius);
        } else {
            // Handle the case when opossum is null
            throw new IllegalArgumentException("Entity (opossum) cannot be null");
        }
    }
}