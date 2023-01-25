package com.dasffafa.fantasia.common.entity.living;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FeatheredSheepEntity extends Mob implements IAnimatable, RangedAttackMob {
    private final AnimationFactory animationFactory = GeckoLibUtil.createFactory(this);
    private FeatheredSheepStatus status = FeatheredSheepStatus.IDLING;
    public FeatheredSheepEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.8f)
                .add(Attributes.FLYING_SPEED, 0.8f)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0f)
                .build();
    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(
                this, "controller", 0,
                this::predicate)
        );
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {

    }

    @Override
    public void animateHurt() {

    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.getAnimatable() instanceof FeatheredSheepEntity featheredSheep) {
            switch (featheredSheep.status) {
                case IDLING -> event.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
                case DYING -> event.getController().setAnimation(new AnimationBuilder().addAnimation("die"));
                case FLYING_SLOW -> event.getController().setAnimation(new AnimationBuilder().addAnimation("fly_loop"));
                case FLYING_FAST -> event.getController().setAnimation(new AnimationBuilder().addAnimation("skill"));
                case CHARGING -> event.getController().setAnimation(new AnimationBuilder().addAnimation("charge"));
                case SWOOPING -> event.getController().setAnimation(new AnimationBuilder().addAnimation("swoop"));
                case WANDERING -> event.getController().setAnimation(new AnimationBuilder().addAnimation("wander"));
            }
        }
        return PlayState.CONTINUE;
    }
}

enum FeatheredSheepStatus {
    SWOOPING,
    FLYING_SLOW,
    FLYING_FAST,
    DYING,
    IDLING,
    WANDERING,
    CHARGING,
}