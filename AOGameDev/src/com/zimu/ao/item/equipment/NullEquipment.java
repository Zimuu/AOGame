package com.zimu.ao.item.equipment;

import com.zimu.ao.character.AbstractChar;
import com.zimu.ao.enums.EquipmentType;

public class NullEquipment extends Equipment {

	public NullEquipment() {
		super("NullEquipment", 50, null, null, null);
		this.equipmentType = EquipmentType.NULL;
	}

	@Override
	public void equip(AbstractChar character) {}

}
