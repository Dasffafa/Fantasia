from operator import add
import os 
import re
import sys
import json
import enum
from string import Template

ROOT_PATH = r"D:\Personal\江哥\Fantasia\forge-1.16.5-36.2.39-mdk"
RESOURCE_PATH = os.path.join(ROOT_PATH, "src", "main", "resources")
ITEM_MODEL_PATH = os.path.join(RESOURCE_PATH, "assets", "fantasia", "models", "item")
BLOCK_MODEL_PATH = os.path.join(RESOURCE_PATH, "assets", "fantasia", "models", "block")
BLOCKSTATE_PATH = os.path.join(RESOURCE_PATH, "assets", "fantasia", "blockstates")
TEXTURE_PATH = os.path.join(RESOURCE_PATH, "assets", "fantasia", "textures")
LANG_PATH = os.path.join(RESOURCE_PATH, "assets", "fantasia", "lang")

ITEM_PATH = os.path.join(ROOT_PATH, r"src\main\java\com\brotherJiang\fantasia\common\item")
BLOCK_PATH = os.path.join(ROOT_PATH, r"src\main\java\com\brotherJiang\fantasia\common\block")

def camel_to_snake(name):
    s1 = re.sub('(.)([A-Z][a-z]+)', r'\1_\2', name)
    return re.sub('([a-z0-9])([A-Z])', r'\1_\2', s1).lower()

def add_item(class_name, en_name, cn_name, type="common_item"):
    snaked_name = camel_to_snake(class_name)

    print('Creating main item class: {}.java...'.format(class_name))
    item_file_path = os.path.join(ITEM_PATH, class_name + ".java")

    if os.path.exists(item_file_path):
        print('{} already exists, skipping registering item...'.format(item_file_path))
        ans = input('Item {} already exists, overwrite? (y/n)'.format(class_name))
        if ans != 'y':
            return
    else:
        print('Adding registering code for the new item...')
        with open(os.path.join(ITEM_PATH,"FantasiaItems.java"),'r',encoding="utf-8") as f:
            content = f.read()
            content = content.replace(
                '    //Following are Items', 
                '    //Following are Items\n    public static final RegistryObject<Item> {} = ITEMS.register("{}", {}::new);'
                .format(snaked_name.upper(),snaked_name, class_name))

        with open(os.path.join(ITEM_PATH,"FantasiaItems.java"),'w',encoding="utf-8") as f:
            f.write(content)

    print('Filling the new item file with code...')
    with open(item_file_path,'w',encoding="utf-8") as f:
        item_content = Template(open('templates/{}.java'.format(type),'r',encoding="utf-8").read())
        f.write(item_content.safe_substitute({'class_name':class_name}))

    print('Creating item model for the item...')
    with open(os.path.join(ITEM_MODEL_PATH,"{}.json".format(snaked_name)),'w',encoding="utf-8") as f:
        item_model = Template(open('templates/common_item_model.json','r',encoding="utf-8").read())
        print(item_model)
        f.write(item_model.safe_substitute({'snaked_name':snaked_name}))

    print('Cheking if the item has texture...')
    if not os.path.exists(os.path.join(TEXTURE_PATH,"item","{}.png".format(snaked_name))):
        print('Warning: {}.png not found. Please consider creating the texture image for the item.'.format(snaked_name))
    else:
        print('Texture for the item was found.')

    print('Adding translation for the item...')
    add_translation("item.fantasia.{}".format(snaked_name), en_name, cn_name)
    print('Done.')

def remove_item(class_name):
    snaked_name = camel_to_snake(class_name)

    item_file_path = os.path.join(ITEM_PATH, class_name + ".java")
    
    if not os.path.exists(item_file_path):
        print('{} not found, skipping removing item...'.format(item_file_path))
        return
    else:
        print('Removing main item class: {}.java...'.format(class_name))
        os.remove(item_file_path)
        print('Removing registering code for the item...')
        with open(os.path.join(ITEM_PATH,"FantasiaItems.java"),'r',encoding="utf-8") as f:
            content = f.read()
            content = content.replace(
                '\n    public static final RegistryObject<Item> {} = ITEMS.register("{}", {}::new);'
                .format(snaked_name.upper(),snaked_name, class_name),
                '')

        with open(os.path.join(ITEM_PATH,"FantasiaItems.java"),'w',encoding="utf-8") as f:
            f.write(content)

    print('Removing item model for the item...')
    os.remove(os.path.join(ITEM_MODEL_PATH,"{}.json".format(snaked_name)))

    print('Removing translation for the item...')
    remove_translation("item.fantasia.{}".format(snaked_name))
    print('Done.')

def add_translation(key, en, zh):
    en_us = json.load(open(os.path.join(LANG_PATH, "en_us.json"),'r',encoding="utf-8"))
    zh_cn = json.load(open(os.path.join(LANG_PATH, "zh_cn.json"),'r',encoding="utf-8"))
    en_us[key] = en
    zh_cn[key] = zh
    json.dump(en_us, open(os.path.join(LANG_PATH, "en_us.json"),'w',encoding="utf-8"),indent=4,ensure_ascii=False)
    json.dump(zh_cn, open(os.path.join(LANG_PATH, "zh_cn.json"),'w',encoding="utf-8"),indent=4,ensure_ascii=False)

def remove_translation(key):
    en_us = json.load(open(os.path.join(LANG_PATH, "en_us.json"),'r',encoding="utf-8"))
    zh_cn = json.load(open(os.path.join(LANG_PATH, "zh_cn.json"),'r',encoding="utf-8"))
    del en_us[key]
    del zh_cn[key]
    json.dump(en_us, open(os.path.join(LANG_PATH, "en_us.json"),'w',encoding="utf-8"),indent=4,ensure_ascii=False)
    json.dump(zh_cn, open(os.path.join(LANG_PATH, "zh_cn.json"),'w',encoding="utf-8"),indent=4,ensure_ascii=False)

def add_block(class_name,en_name,cn_name,type='common_block',blockstate='common'):
    snaked_name = camel_to_snake(class_name)

    print('Creating main block class: {}.java...'.format(class_name))
    block_file_path = os.path.join(BLOCK_PATH, class_name + ".java")

    if os.path.exists(block_file_path):
        print('{} already exists, skipping registering block...'.format(block_file_path))
        ans = input('Block {} already exists, overwrite? (y/n)'.format(class_name))
        if ans != 'y':
            return
    else:
        print('Adding registering code for the new block...')
        with open(os.path.join(BLOCK_PATH,"FantasiaBlocks.java"),'r',encoding="utf-8") as f:
            content = f.read()
            content2 = content.replace(
                '    //Followings are blocks', 
                '    //Followings are blocks\n    public static final RegistryObject<Block> {} = BLOCKS.register("{}", {}::new);'
                .format(snaked_name.upper(),snaked_name, class_name))

        with open(os.path.join(BLOCK_PATH,"FantasiaBlocks.java"),'w',encoding="utf-8") as f:
            f.write(content2)

        print('Creating ItemBlock for the block...')
        with open(os.path.join(ITEM_PATH,"FantasiaItems.java"),'r',encoding="utf-8") as f:
                content = f.read()
                content = content.replace(
                    '    //Followings are ItemBlocks', 
                    '    //Followings are ItemBlocks\n    public static final RegistryObject<Item> {} = ITEMS.register("{}", () -> new BlockItem(FantasiaBlocks.{}.get(), new Item.Properties().group(Fantasia.GROUP)));'
                    .format(snaked_name.upper(),snaked_name,snaked_name.upper()))
        with open(os.path.join(ITEM_PATH,"FantasiaItems.java"),'w',encoding="utf-8") as f:
            f.write(content)

    print('Filling the new block file with code...')
    with open(block_file_path,'w',encoding="utf-8") as f:
        block_content = Template(open('templates/{}.java'.format(type),'r',encoding="utf-8").read())
        f.write(block_content.safe_substitute(class_name=class_name))

    print('Creating blockstate for the block...')
    with open(os.path.join(RESOURCE_PATH,'assets/fantasia/blockstates/{}.json'.format(snaked_name)),'w',encoding="utf-8") as f:
        blockstate_template = Template(open('templates/{}_blockstate.json'.format(blockstate),'r',encoding="utf-8").read())
        f.write(blockstate_template.safe_substitute(snaked_name=snaked_name))

    print('Creating block model for the block...')
    with open(os.path.join(BLOCK_MODEL_PATH,"{}.json".format(snaked_name)),'w',encoding="utf-8") as f:
        block_model = Template(open('templates/common_block_model.json','r',encoding="utf-8").read())
        f.write(block_model.safe_substitute(snaked_name=snaked_name))

    print('Cheking if the block has texture...')
    if not os.path.exists(os.path.join(TEXTURE_PATH,"block","{}.png".format(snaked_name))):
        print('Warning: {}.png not found. Please consider creating the texture image for the block.'.format(snaked_name))
    else:
        print('Texture for the block was found.')

    print('Adding translation for the block...')
    add_translation('block.fantasia.{}'.format(snaked_name),en_name,cn_name)
    print('Done.')
if __name__ == "__main__":
    add_item("PoopSword", "Poop Sword", "尸米剑")
#     add_block('FirstBlock', 'First Block', '第一个方块',type='common_block',blockstate='common')




