package net.minecraft.world.level;

import com.google.common.collect.Iterables;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface CollisionGetter extends BlockGetter {
    WorldBorder getWorldBorder();

    @Nullable
    BlockGetter getChunkForCollisions(int p_45774_, int p_45775_);

    default boolean isUnobstructed(@Nullable Entity p_45750_, VoxelShape p_45751_) {
        return true;
    }

    default boolean isUnobstructed(BlockState p_45753_, BlockPos p_45754_, CollisionContext p_45755_) {
        VoxelShape voxelshape = p_45753_.getCollisionShape(this, p_45754_, p_45755_);
        return voxelshape.isEmpty()
            || this.isUnobstructed(null, voxelshape.move((double)p_45754_.getX(), (double)p_45754_.getY(), (double)p_45754_.getZ()));
    }

    default boolean isUnobstructed(Entity p_45785_) {
        return this.isUnobstructed(p_45785_, Shapes.create(p_45785_.getBoundingBox()));
    }

    default boolean noCollision(AABB p_45773_) {
        return this.noCollision(null, p_45773_);
    }

    default boolean noCollision(Entity p_45787_) {
        return this.noCollision(p_45787_, p_45787_.getBoundingBox());
    }

    default boolean noCollision(@Nullable Entity p_45757_, AABB p_45758_) {
        for (VoxelShape voxelshape : this.getBlockCollisions(p_45757_, p_45758_)) {
            if (!voxelshape.isEmpty()) {
                return false;
            }
        }

        if (!this.getEntityCollisions(p_45757_, p_45758_).isEmpty()) {
            return false;
        } else if (p_45757_ == null) {
            return true;
        } else {
            VoxelShape voxelshape1 = this.borderCollision(p_45757_, p_45758_);
            return voxelshape1 == null || !Shapes.joinIsNotEmpty(voxelshape1, Shapes.create(p_45758_), BooleanOp.AND);
        }
    }

    default boolean noBlockCollision(@Nullable Entity p_299893_, AABB p_300925_) {
        for (VoxelShape voxelshape : this.getBlockCollisions(p_299893_, p_300925_)) {
            if (!voxelshape.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    List<VoxelShape> getEntityCollisions(@Nullable Entity p_186427_, AABB p_186428_);

    default Iterable<VoxelShape> getCollisions(@Nullable Entity p_186432_, AABB p_186433_) {
        List<VoxelShape> list = this.getEntityCollisions(p_186432_, p_186433_);
        Iterable<VoxelShape> iterable = this.getBlockCollisions(p_186432_, p_186433_);
        return list.isEmpty() ? iterable : Iterables.concat(list, iterable);
    }

    default Iterable<VoxelShape> getBlockCollisions(@Nullable Entity p_186435_, AABB p_186436_) {
        return () -> new BlockCollisions<>(this, p_186435_, p_186436_, false, (p_286215_, p_286216_) -> p_286216_);
    }

    @Nullable
    private VoxelShape borderCollision(Entity p_186441_, AABB p_186442_) {
        WorldBorder worldborder = this.getWorldBorder();
        return worldborder.isInsideCloseToBorder(p_186441_, p_186442_) ? worldborder.getCollisionShape() : null;
    }

    default boolean collidesWithSuffocatingBlock(@Nullable Entity p_186438_, AABB p_186439_) {
        BlockCollisions<VoxelShape> blockcollisions = new BlockCollisions<>(this, p_186438_, p_186439_, true, (p_286211_, p_286212_) -> p_286212_);

        while (blockcollisions.hasNext()) {
            if (!blockcollisions.next().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    default Optional<BlockPos> findSupportingBlock(Entity p_286468_, AABB p_286792_) {
        BlockPos blockpos = null;
        double d0 = Double.MAX_VALUE;
        BlockCollisions<BlockPos> blockcollisions = new BlockCollisions<>(this, p_286468_, p_286792_, false, (p_286213_, p_286214_) -> p_286213_);

        while (blockcollisions.hasNext()) {
            BlockPos blockpos1 = blockcollisions.next();
            double d1 = blockpos1.distToCenterSqr(p_286468_.position());
            if (d1 < d0 || d1 == d0 && (blockpos == null || blockpos.compareTo(blockpos1) < 0)) {
                blockpos = blockpos1.immutable();
                d0 = d1;
            }
        }

        return Optional.ofNullable(blockpos);
    }

    default Optional<Vec3> findFreePosition(@Nullable Entity p_151419_, VoxelShape p_151420_, Vec3 p_151421_, double p_151422_, double p_151423_, double p_151424_) {
        if (p_151420_.isEmpty()) {
            return Optional.empty();
        } else {
            AABB aabb = p_151420_.bounds().inflate(p_151422_, p_151423_, p_151424_);
            VoxelShape voxelshape = StreamSupport.stream(this.getBlockCollisions(p_151419_, aabb).spliterator(), false)
                .filter(p_186430_ -> this.getWorldBorder() == null || this.getWorldBorder().isWithinBounds(p_186430_.bounds()))
                .flatMap(p_186426_ -> p_186426_.toAabbs().stream())
                .map(p_186424_ -> p_186424_.inflate(p_151422_ / 2.0, p_151423_ / 2.0, p_151424_ / 2.0))
                .map(Shapes::create)
                .reduce(Shapes.empty(), Shapes::or);
            VoxelShape voxelshape1 = Shapes.join(p_151420_, voxelshape, BooleanOp.ONLY_FIRST);
            return voxelshape1.closestPointTo(p_151421_);
        }
    }
}