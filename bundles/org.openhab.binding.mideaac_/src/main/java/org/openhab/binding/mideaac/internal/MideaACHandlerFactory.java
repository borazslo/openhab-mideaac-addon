/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.mideaac.internal;

import static org.openhab.binding.mideaac.internal.MideaACBindingConstants.SUPPORTED_THING_TYPES_UIDS;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.mideaac.internal.handler.MideaACHandler;
import org.openhab.core.i18n.UnitProvider;
import org.openhab.core.net.NetworkAddressService;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link MideaACHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Jacek Dobrowolski
 */
@NonNullByDefault
@Component(configurationPid = "binding.mideaac", service = ThingHandlerFactory.class)
public class MideaACHandlerFactory extends BaseThingHandlerFactory {

    // private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Set.of(THING_TYPE_SAMPLE);

    private final NetworkAddressService networkAddressService;
    private UnitProvider unitProvider;

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    // @Override
    // protected @Nullable ThingHandler createHandler(Thing thing) {
    // ThingTypeUID thingTypeUID = thing.getThingTypeUID();
    //
    // if (THING_TYPE_MIDEAAC.equals(thingTypeUID)) {
    // return new MideaACHandler(thing);
    // }
    //
    // return null;
    // }

    @Activate
    public MideaACHandlerFactory(@Reference NetworkAddressService networkAddressService,
            @Reference UnitProvider unitProvider) {
        this.networkAddressService = networkAddressService;
        this.unitProvider = unitProvider;
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();
        if (SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID)) {
            return new MideaACHandler(thing, networkAddressService.getPrimaryIpv4HostAddress(), unitProvider);
        }
        return null;
    }
}
