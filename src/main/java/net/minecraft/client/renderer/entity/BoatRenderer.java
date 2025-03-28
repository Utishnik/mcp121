package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ChestRaftModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.model.WaterPatchModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class BoatRenderer extends EntityRenderer<Boat> {
    private final Map<Boat.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public BoatRenderer(EntityRendererProvider.Context p_234563_, boolean p_234564_) {
        super(p_234563_);
        this.shadowRadius = 0.8F;
        this.boatResources = Stream.of(Boat.Type.values())
            .collect(
                ImmutableMap.toImmutableMap(
                    p_173938_ -> (Boat.Type)p_173938_, p_340938_ -> Pair.of(getTextureLocation(p_340938_, p_234564_), this.createBoatModel(p_234563_, p_340938_, p_234564_))
                )
            );
    }

    private ListModel<Boat> createBoatModel(EntityRendererProvider.Context p_248834_, Boat.Type p_249317_, boolean p_250093_) {
        ModelLayerLocation modellayerlocation = p_250093_ ? ModelLayers.createChestBoatModelName(p_249317_) : ModelLayers.createBoatModelName(p_249317_);
        ModelPart modelpart = p_248834_.bakeLayer(modellayerlocation);
        if (p_249317_ == Boat.Type.BAMBOO) {
            return (ListModel<Boat>)(p_250093_ ? new ChestRaftModel(modelpart) : new RaftModel(modelpart));
        } else {
            return (ListModel<Boat>)(p_250093_ ? new ChestBoatModel(modelpart) : new BoatModel(modelpart));
        }
    }

    private static ResourceLocation getTextureLocation(Boat.Type p_234566_, boolean p_234567_) {
        return p_234567_
            ? ResourceLocation.withDefaultNamespace("textures/entity/chest_boat/" + p_234566_.getName() + ".png")
            : ResourceLocation.withDefaultNamespace("textures/entity/boat/" + p_234566_.getName() + ".png");
    }

    public void render(Boat p_113929_, float p_113930_, float p_113931_, PoseStack p_113932_, MultiBufferSource p_113933_, int p_113934_) {
        p_113932_.pushPose();
        p_113932_.translate(0.0F, 0.375F, 0.0F);
        p_113932_.mulPose(Axis.YP.rotationDegrees(180.0F - p_113930_));
        float f = (float)p_113929_.getHurtTime() - p_113931_;
        float f1 = p_113929_.getDamage() - p_113931_;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            p_113932_.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)p_113929_.getHurtDir()));
        }

        float f2 = p_113929_.getBubbleAngle(p_113931_);
        if (!Mth.equal(f2, 0.0F)) {
            p_113932_.mulPose(new Quaternionf().setAngleAxis(p_113929_.getBubbleAngle(p_113931_) * (float) (Math.PI / 180.0), 1.0F, 0.0F, 1.0F));
        }

        Pair<ResourceLocation, ListModel<Boat>> pair = this.boatResources.get(p_113929_.getVariant());
        ResourceLocation resourcelocation = pair.getFirst();
        ListModel<Boat> listmodel = pair.getSecond();
        p_113932_.scale(-1.0F, -1.0F, 1.0F);
        p_113932_.mulPose(Axis.YP.rotationDegrees(90.0F));
        listmodel.setupAnim(p_113929_, p_113931_, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = p_113933_.getBuffer(listmodel.renderType(resourcelocation));
        listmodel.renderToBuffer(p_113932_, vertexconsumer, p_113934_, OverlayTexture.NO_OVERLAY);
        if (!p_113929_.isUnderWater()) {
            VertexConsumer vertexconsumer1 = p_113933_.getBuffer(RenderType.waterMask());
            if (listmodel instanceof WaterPatchModel waterpatchmodel) {
                waterpatchmodel.waterPatch().render(p_113932_, vertexconsumer1, p_113934_, OverlayTexture.NO_OVERLAY);
            }
        }

        p_113932_.popPose();
        super.render(p_113929_, p_113930_, p_113931_, p_113932_, p_113933_, p_113934_);
    }

    public ResourceLocation getTextureLocation(Boat p_113927_) {
        return this.boatResources.get(p_113927_.getVariant()).getFirst();
    }
}